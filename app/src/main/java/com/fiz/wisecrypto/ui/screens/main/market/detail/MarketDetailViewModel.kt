package com.fiz.wisecrypto.ui.screens.main.market.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.domain.use_case.GetLabelForChart
import com.fiz.wisecrypto.ui.screens.main.models.toActiveUi
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketDetailViewModel @Inject constructor(
    private val formatUseCase: FormatUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val getLabelForChart: GetLabelForChart,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

    ) : BaseViewModel() {
    var viewState by mutableStateOf(MarketDetailViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketDetailViewEffect>()
        private set

    var idCoin: String? = null
    var monthNames: List<String>? = null
    var daysNames: List<String>? = null

    var active: Active? = null
    var email: String? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    email = user.email
                    active = user.actives.find { idCoin == it.id }

                    viewState = viewState.copy(
                        isWatchList = user.watchList.contains(idCoin)
                    )
                    refresh()
                }
        }
    }

    fun onEvent(event: MarketDetailEvent) {
        when (event) {
            MarketDetailEvent.OnRefresh -> onRefresh()
            MarketDetailEvent.Started -> started()
            MarketDetailEvent.Stopped -> stopped()
            MarketDetailEvent.BackButtonClicked -> backButtonClicked()
            MarketDetailEvent.AddWatchListClicked -> addWatchListClicked()
            MarketDetailEvent.BuyButtonClicked -> buyButtonClicked()
            MarketDetailEvent.SellButtonClicked -> sellButtonClicked()
            is MarketDetailEvent.PeriodFilterChipClicked -> periodFilterChipClicked(event.index)
        }
    }

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailViewEffect.MoveReturn)
        }
    }

    private fun addWatchListClicked() {
        viewModelScope.launch {
            if (viewState.isWatchList)
                userRepository.removeWatchList(
                    email ?: return@launch,
                    idCoin ?: return@launch
                )
            else
                userRepository.addWatchList(email ?: return@launch, idCoin ?: return@launch)
        }
    }

    private fun buyButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailViewEffect.MoveMarketBuyScreen)
        }
    }

    private fun sellButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(MarketDetailViewEffect.MoveMarketSellScreen)
        }
    }

    private fun periodFilterChipClicked(index: Int) {
        viewModelScope.launch {
            viewState = viewState.copy(
                indexPeriodFilterChip = PeriodFilterChip.values()[index]
            )
            refresh()
        }
    }

    override suspend fun refresh() {
        idCoin?.let {
            viewState = viewState.copy(isLoading = true)
            val coinHistoryDayResponse =
                viewModelScope.async { coinRepository.getCoinHistory(it, PeriodFilterChip.Day) }
            val coinHistoryResponse = viewModelScope.async {
                coinRepository.getCoinHistory(
                    it,
                    viewState.indexPeriodFilterChip
                )
            }
            val coinResponse = viewModelScope.async { coinRepository.getCoin(it) }
            val coinsResponse = viewModelScope.async { coinRepository.getCoins() }

            var errorMessage: String? = ""

            when (val result = coinHistoryResponse.await()) {
                is Resource.Success -> {
                    val coinMarketChartRange = result.data ?: return

                    val currentPriceHistoryLabel =
                        getLabelForChart(
                            coinMarketChartRange.prices,
                            viewState.indexPeriodFilterChip,
                            monthNames?: emptyList(),
                            daysNames?: emptyList()
                        )

                    viewState = viewState.copy(
                        currentPriceHistoryValue = coinMarketChartRange.prices,
                        currentPriceHistoryLabel = currentPriceHistoryLabel
                    )
                }
                is Resource.Error -> {
                    errorMessage = result.message
                }
            }

            var totalVolume24h = 0.0
            when (val result = coinHistoryDayResponse.await()) {
                is Resource.Success -> {
                    val coinMarketChartRange = result.data ?: return
                    totalVolume24h = coinMarketChartRange.totalVolumes.first().value
                }
                is Resource.Error -> {
                    errorMessage = result.message
                }
            }

            when (val result = coinResponse.await()) {
                is Resource.Success -> {
                    val coin = result.data ?: return
                    viewState = viewState.copy(
                        name = coin.name,
                        priceOne = formatUseCase.getFormatPricePortfolio(coin.currentPrice),
                        abbreviated = coin.symbol,
                        allTimeHigh = formatUseCase.getFormatOverview(coin.ath),
                        allTimeLow = formatUseCase.getFormatOverview(coin.atl),
                        marketCap = formatUseCase.getFormatOverview(coin.marketCap),
                        totalVolume = formatUseCase.getFormatOverview(coin.totalVolume),
                        totalSupply = formatUseCase.getFormatOverview(coin.totalSupply),
                        maxSupply = formatUseCase.getFormatOverview(coin.maxSupply),
                        marketCapChange24h = formatUseCase.getFormatOverview(coin.marketCapChange24h),
                        totalVolumeChange24h = formatUseCase.getFormatOverview(coin.totalVolume - totalVolume24h),
                    )
                }
                is Resource.Error -> {
                    errorMessage = result.message
                }
            }


            when (val result = coinsResponse.await()) {
                is Resource.Success -> {
                    val coins = result.data ?: listOf()
                    val coin = coins.find { idCoin == it.id } ?: return
                    val yourActive = active?.toActiveUi(coins)

                    val pricePortfolioIncreased = coin.priceChangePercentage > 0
                    val changePercentagePricePortfolio =
                        formatUseCase.getFormatChangePercentagePricePortfolio(coin.priceChangePercentage)

                    viewState = viewState.copy(
                        yourActive = yourActive,
                        pricePortfolioIncreased = pricePortfolioIncreased,
                        changePercentagePricePortfolio = changePercentagePricePortfolio,
                    )
                }
                is Resource.Error -> {
                    errorMessage = result.message
                }
            }

            viewState = viewState.copy(isLoading = false)

            if (errorMessage!="")
                viewEffect.emit(
                    MarketDetailViewEffect.ShowError(
                        errorMessage
                    )
                )
        }
    }

    fun getUpperValue(upperValue:Double):String{
        return formatUseCase.getFormatBalance(upperValue)
    }
}

