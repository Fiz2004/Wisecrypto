package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumnWithoutBottomBar
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.components.PortfolioInfoWithTotalReturn
import com.fiz.wisecrypto.ui.screens.main.home.portfolio.components.YourActiveFullInfo

@Composable
fun HomePortfolioScreen(
    viewModel: HomePortfolioViewModel = viewModel(),
    moveHomeMainScreen: () -> Unit,
    moveHomeDetailScreen: (String) -> Unit
) {

    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onEvent(HomePortfolioEvent.Started)
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onEvent(HomePortfolioEvent.Stopped)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewEffect.collect { effect ->
            when (effect) {
                HomePortfolioViewEffect.MoveReturn -> {
                    moveHomeMainScreen()
                }
                is HomePortfolioViewEffect.ShowError -> {
                    val text = context.getString(
                        if (effect.message == null)
                            R.string.error_network_default
                        else
                            R.string.error_network, effect.message
                    )
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    MainColumnWithoutBottomBar(
        textToolbar = stringResource(R.string.portfolio_portfolio),
        onClickBackButton = { viewModel.onEvent(HomePortfolioEvent.BackButtonClicked) }
    ) {
        PortfolioInfoWithTotalReturn(
            viewState.pricePortfolio,
            viewState.totalReturn,
            viewState.pricePortfolioIncreased,
            viewState.changePercentagePricePortfolio
        )
        Spacer(modifier = Modifier.height(24.dp))
        YourActiveFullInfo(viewState.portfolio,
            activeClicked = { id -> moveHomeDetailScreen(id) })
    }
}



