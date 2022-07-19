package com.fiz.wisecrypto.ui.screens.main.market.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.domain.models.Coin
import com.fiz.wisecrypto.domain.models.User
import com.fiz.wisecrypto.domain.use_case.CurrentUserUseCase
import com.fiz.wisecrypto.ui.util.BaseViewModel
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    private val coinRepository: CoinRepositoryImpl
) : BaseViewModel() {
    var viewState by mutableStateOf(MarketViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketViewEffect>()
        private set

    private var user: User? = null
    private var coins: List<Coin>? = null

    init {
        viewModelScope.launch {
            currentUserUseCase.getCurrentUser()
                .collectLatest {
                    user = it
                    refresh()
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

    override suspend fun refresh() {
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

    private suspend fun getNewFilterCoins(): List<Coin> {
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
        return result
    }

    private fun marketChipClicked(index: Int) {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            viewState = viewState
                .copy(selectedChipNumber = index)
            val newCoins = getNewFilterCoins()
            viewState = viewState
                .copy(coins = newCoins)
            viewState = viewState.copy(isLoading = false)
        }
    }

    private fun searchTextChanged(value: String) {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            viewState = viewState
                .copy(searchText = value)
            val newCoins = getNewFilterCoins()
            viewState = viewState
                .copy(coins = newCoins)
            viewState = viewState.copy(isLoading = false)
        }
    }

}