package com.fiz.wisecrypto.ui.screens.main.market.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.ui.screens.main.models.CoinUi
import com.fiz.wisecrypto.ui.screens.main.models.toCoinUi
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val currentUserUseCase: CurrentUserUseCase,
    private val coinRepository: CoinRepositoryImpl
) : BaseViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var viewState by savedStateHandle.saveable {
        mutableStateOf(MarketViewState())
    }
        private set

    var viewEffect = MutableSharedFlow<MarketViewEffect>()
        private set

    private var user: User? = null
    private var coins: List<Coin>? = null

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest {
                    user = it
                    request()
                }
        }
    }

    fun onEvent(event: MarketEvent) {
        when (event) {
            MarketEvent.OnRefresh -> onRefresh()
            MarketEvent.Started -> started()
            MarketEvent.Stopped -> stopped()
            is MarketEvent.SearchTextChanged -> searchTextChanged(event.value)
            is MarketEvent.MarketChipClicked -> marketChipClicked(event.index)
            is MarketEvent.YourActiveClicked -> yourActiveClicked(event.id)
        }
    }

    private fun yourActiveClicked(id: String) {
        viewModelScope.launch {
            viewEffect.emit(MarketViewEffect.MoveHomeDetailScreen(id))
        }
    }

    override suspend fun request() {
        user?.let {
            viewState = viewState.copy(isLoading = true)
            when (val result = coinRepository.getCoins()) {
                is Resource.Success -> {
                    coins = result.data
                    viewState = viewState.copy(coins = getNewFilterCoins())
                }
                is Resource.Error -> viewEffect.emit(MarketViewEffect.ShowError(result.message))
            }
            viewState = viewState.copy(isLoading = false)
        }
    }

    private fun getNewFilterCoins(): List<CoinUi> {
        val watchlist = user?.watchList ?: listOf()
        val checkValue = viewState.searchText.lowercase()

        val newCoins = coins?.filter {
            it.name.lowercase().contains(checkValue)
                    || it.market.lowercase().contains(checkValue)
                    || it.symbol.lowercase().contains(checkValue)
        } ?: listOf()

        val result = when (viewState.selectedChipNumber) {
            0 -> currentUserUseCase.getCoinsWatchList(watchlist, newCoins)
            else -> newCoins
        }
        return result.map { it.toCoinUi() }
    }

    private fun marketChipClicked(index: Int) {
        viewState = viewState
            .copy(selectedChipNumber = index)
        viewState = viewState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.Default) {
            val newCoins = getNewFilterCoins()
            viewState = viewState
                .copy(coins = newCoins)
            viewState = viewState.copy(isLoading = false)
        }
    }

    private fun searchTextChanged(value: String) {
        viewState = viewState.copy(searchText = value)
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.Default) {
            delay(500L)
            viewState = viewState.copy(isLoading = true)
            val newCoins = getNewFilterCoins()
            viewState = viewState
                .copy(coins = newCoins)
            viewState = viewState.copy(isLoading = false)
        }
    }

}