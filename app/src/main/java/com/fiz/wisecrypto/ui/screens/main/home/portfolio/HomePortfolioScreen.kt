package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.FullScreenLoading
import com.fiz.wisecrypto.ui.screens.main.components.LoadingContent
import com.fiz.wisecrypto.ui.screens.main.components.MainColumn
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.components.PortfolioInfoWithTotalReturn
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.components.YourActiveFullInfo
import com.fiz.wisecrypto.ui.util.LifeCycleEffect
import com.fiz.wisecrypto.util.showError

@Composable
fun HomePortfolioScreen(
    viewModel: HomePortfolioViewModel = viewModel(),
    moveHomeMainScreen: () -> Unit,
    moveMarketDetailScreen: (String) -> Unit
) {
    LifeCycleEffect(viewModel)
    ReactEffect(viewModel, moveHomeMainScreen, moveMarketDetailScreen)

    val viewState = viewModel.viewState
    LoadingContent(
        empty = viewState.portfolio == null,
        emptyContent = { FullScreenLoading() },
        loading = viewState.isLoading,
        onRefresh = { viewModel.onEvent(HomePortfolioEvent.OnRefresh) }
    ) {
        MainColumn(
            textToolbar = stringResource(R.string.portfolio_portfolio),
            onClickBackButton = { viewModel.onEvent(HomePortfolioEvent.BackButtonClicked) }
        ) {
            PortfolioInfoWithTotalReturn(
                balancePortfolio = viewState.balancePortfolio,
                totalReturn = viewState.totalReturn,
                isPricePortfolioIncreased = viewState.isPricePortfolioIncreased,
                percentageChangedBalance = viewState.percentageChangedBalance
            )
            Spacer(modifier = Modifier.height(24.dp))
            YourActiveFullInfo(viewState.portfolio ?: emptyList(),
                activeClicked = { id ->
                    viewModel.onEvent(HomePortfolioEvent.YourActiveClicked(id))
                })
        }
    }
}

@Composable
private fun ReactEffect(
    viewModel: HomePortfolioViewModel,
    moveHomeMainScreen: () -> Unit,
    moveHomeDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current
    val viewEffect = viewModel.viewEffect
    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomePortfolioViewEffect.MoveReturn -> moveHomeMainScreen()
                is HomePortfolioViewEffect.MoveHomeDetailScreen -> moveHomeDetailScreen(effect.id)
                is HomePortfolioViewEffect.ShowError -> showError(context, effect.message)
            }
        }
    }
}



