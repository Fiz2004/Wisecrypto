package com.fiz.wisecrypto.ui.screens.main.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.util.Consts.TIME_REFRESH_NETWORK_MS
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val coinRepository: CoinRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(MarketViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketViewEffect>()
        private set

    private var jobRefresh: Job? = null

    var watchlist: List<String> = listOf()

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest { user ->
                    user ?: return@collectLatest

                    watchlist = user.watchList
                }
        }
    }

    fun onEvent(event: MarketEvent) {
        when (event) {
            is MarketEvent.SearchTextChanged -> searchTextChanged(event.value)
            is MarketEvent.MarketChipClicked -> marketChipClicked(event.index)
            MarketEvent.Started -> started()
            MarketEvent.Stopped -> stopped()
            is MarketEvent.YourActiveClicked -> yourActiveClicked(event.id)
        }
    }

    private fun yourActiveClicked(id: String) {
        viewModelScope.launch {
            viewEffect.emit(MarketViewEffect.MoveHomeDetailScreen(id))
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
                    delay(TIME_REFRESH_NETWORK_MS.toLong())
                }
            }
    }

    private suspend fun refreshState() {
        viewState = viewState.copy(isLoading = true)
        val result = coinRepository.getCoins()
        if (result is Resource.Success)
            viewState = viewState.copy(coins = filterCoins())
        else
            viewEffect.emit(
                MarketViewEffect.ShowError(
                    result.message
                )
            )
        viewState = viewState.copy(isLoading = false)
    }

    private suspend fun filterCoins(): List<Coin> {
        val checkValue = viewState.searchText.lowercase()

        var newCoins = coinRepository.getCoins().data?.filter {
            it.name.lowercase().contains(checkValue)
                    || it.market.lowercase().contains(checkValue)
                    || it.symbol.lowercase().contains(checkValue)
        } ?: listOf()

        newCoins = when (viewState.selectedChipNumber) {
            0 -> {
                currentUserUseCase.getCoinsWatchList(watchlist, newCoins)
            }
            else -> {
                newCoins
            }
        }

        return newCoins
    }

    private fun marketChipClicked(index: Int) {
        viewModelScope.launch {
            viewState = viewState.copy(selectedChipNumber = index)
            viewState = viewState.copy(coins = filterCoins())
        }
    }

    private fun searchTextChanged(value: String) {
        viewModelScope.launch {
            viewState = viewState.copy(searchText = value)
            viewState = viewState.copy(coins = filterCoins())
        }
    }

}