package com.fiz.wisecrypto.ui.screens.main.home.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.data.repositories.UserRepositoryImpl
import com.fiz.wisecrypto.domain.models.Active
import com.fiz.wisecrypto.domain.models.History
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.domain.use_case.FormatUseCase
import com.fiz.wisecrypto.domain.use_case.PortfolioUseCase
import com.fiz.wisecrypto.ui.screens.main.models.toActiveUi
import com.fiz.wisecrypto.util.Consts
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    private val formatUseCase: FormatUseCase,
    private val currentUserUseCase: CurrentUserUseCase,
    private val portfolioUseCase: PortfolioUseCase,
    private val userRepository: UserRepositoryImpl,
    private val coinRepository: CoinRepositoryImpl,

    ) : ViewModel() {
    var viewState by mutableStateOf(HomeDetailViewState())
        private set

    var viewEffect = MutableSharedFlow<HomeDetailViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var idCoin: String? = null

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
                    refreshState()
                }
        }
    }

    private fun stopped() {
        viewModelScope.launch {
            jobRefresh?.cancelAndJoin()
            jobRefresh = null
        }
    }

    private fun started() {
        if (jobRefresh == null)
            jobRefresh = viewModelScope.launch(Dispatchers.Default) {
                while (isActive) {
                    refreshState()
                    delay(Consts.TIME_REFRESH_NETWORK_MS.toLong())
                }
            }
    }

    private suspend fun refreshState() {

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

            var error = false
            var errorMessage: String? = ""

            when (val result = coinHistoryResponse.await()) {
                is Resource.Success -> {
                    val coinMarketChartRange = result.data ?: return

                    val currentPriceHistoryLabel =
                        mapToLabel(coinMarketChartRange.prices, viewState.indexPeriodFilterChip)

                    viewState = viewState.copy(
                        currentPriceHistoryValue = coinMarketChartRange.prices,
                        currentPriceHistoryLabel = currentPriceHistoryLabel
                    )
                }
                is Resource.Error -> {
                    error = true
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
                    error = true
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
                    error = true
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
                    error = true
                    errorMessage = result.message
                }
            }

            viewState = viewState.copy(isLoading = false)

            if (error)
                viewEffect.emit(
                    HomeDetailViewEffect.ShowError(
                        errorMessage
                    )
                )
        }
    }

    private fun mapToLabel(
        prices: List<History>,
        indexPeriodFilterChip: PeriodFilterChip
    ): List<String> {
        return when (indexPeriodFilterChip) {
            PeriodFilterChip.Day -> getLabelsForDay(prices)
            PeriodFilterChip.Week -> getLabelsForWeek(prices)
            PeriodFilterChip.Month -> getLabelsForMonth(prices)
            PeriodFilterChip.Year -> getLabelsForYear(prices)
        }
    }

    private fun getLabelsForYear(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        val daysNames = listOf(
            "Янв",
            "Фвр",
            "Мрт",
            "Апр",
            "Май",
            "Июнь",
            "Июль",
            "Авг",
            "Снт",
            "Окт",
            "Нбр",
            "Дкб"
        )
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val month = value.time.month.value
            val res = daysNames[month - 1]
            result.add(res)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForMonth(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val month = value.time.dayOfMonth
            val res = month.toString()
            result.add(res)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForWeek(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        val daysNames = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            val dayOfWeek = value.time.dayOfWeek.value
            val res = daysNames[dayOfWeek - 1]
            result.add(res)
        }
        result.reverse()
        return result
    }

    private fun getLabelsForDay(prices: List<History>): MutableList<String> {
        val result = mutableListOf<String>()
        for (index in prices.lastIndex downTo 0 step prices.lastIndex / 6) {
            val value = prices[index]
            var hour = value.time.hour
            val minute = when (value.time.minute) {
                in 0..14 -> 0
                in 14..44 -> 30
                else -> {
                    hour += 1
                    0
                }
            }
            val res = hour.toString().padStart(2, '0') + "." +
                    minute.toString().padStart(2, '0')
            result.add(res)
        }
        result.reverse()
        return result
    }

    fun onEvent(event: HomeDetailEvent) {
        when (event) {
            HomeDetailEvent.BackButtonClicked -> backButtonClicked()
            HomeDetailEvent.Started -> started()
            HomeDetailEvent.Stopped -> stopped()
            HomeDetailEvent.AddWatchListClicked -> addWatchListClicked()
            is HomeDetailEvent.Create -> create(event.id)
            is HomeDetailEvent.PeriodFilterChipClicked -> periodFilterChipClicked(event.index)
        }
    }

    private fun periodFilterChipClicked(index: Int) {
        viewModelScope.launch {
            viewState = viewState.copy(
                indexPeriodFilterChip = PeriodFilterChip.values()[index]
            )
            refreshState()
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

    private fun backButtonClicked() {
        viewModelScope.launch {
            viewEffect.emit(HomeDetailViewEffect.MoveReturn)
        }
    }

    private fun create(id: String) {
        viewModelScope.launch {
            idCoin = id
        }
    }
}