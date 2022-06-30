package com.fiz.wisecrypto.ui.screens.main.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.data.data_source.coinsStore
import com.fiz.wisecrypto.data.repositories.CoinRepositoryImpl
import com.fiz.wisecrypto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val coinRepositoryImpl: CoinRepositoryImpl
) : ViewModel() {
    var viewState by mutableStateOf(MarketViewState())
        private set

    var viewEffect = MutableSharedFlow<MarketViewEffect>()
        private set

    init {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            val result = coinRepositoryImpl.getCoins()
            if (result is Resource.Success)
                viewState = viewState.copy(coins = result.data ?: emptyList())
            else
                viewEffect.emit(MarketViewEffect.ShowError("Сбой загрузки из сети"))
            viewState = viewState.copy(isLoading = false)
        }
    }

    fun onEvent(event: MarketEvent) {
        when (event) {
            is MarketEvent.SearchTextChanged -> searchTextChanged(event.value)
            is MarketEvent.MarketChipClicked -> marketChipClicked(event.index)
        }
    }

    private fun marketChipClicked(index: Int) {
        viewModelScope.launch {
            val newCoins = when (index) {
                0 -> coinRepositoryImpl.getCoins().data
                else -> {
                    coinRepositoryImpl.getCoins().data?.filter { it.market == "FIAT" }
                }
            }
            viewState = viewState.copy(selectedChipNumber = index, coins = newCoins ?: emptyList())
        }
    }

    private fun searchTextChanged(value: String) {
        viewModelScope.launch {
            val checkValue = value.lowercase()
            val newCoins = coinsStore.filter {
                it.name.lowercase().contains(checkValue)
                        || it.market.lowercase().contains(checkValue)
                        || it.abbreviated.lowercase().contains(checkValue)
            }
            viewState = viewState.copy(searchText = value, coins = newCoins)
        }
    }

}