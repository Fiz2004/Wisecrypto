package com.fiz.wisecrypto.ui.screens.main.home.portfolio

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.components.MainColumnWithoutBottomBar
import com.fiz.wisecrypto.ui.screens.main.home.components.RelativeLabel
import com.fiz.wisecrypto.ui.screens.main.models.ActiveUi
import com.fiz.wisecrypto.ui.theme.hint
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.abs

@Composable
fun HomePortfolioScreen(
    viewModel: HomePortfolioViewModel = viewModel(),
    moveHomeMain: () -> Unit
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
                    moveHomeMain()
                }
                is HomePortfolioViewEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
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
            viewState.changePercentageBalance
        )
        Spacer(modifier = Modifier.height(24.dp))
        YourActiveFullInfo(viewState.portfolio)
    }
}

@Composable
fun YourActiveFullInfo(portfolio: List<ActiveUi>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        portfolio.forEach {
            item {
                YourActiveItemFullInfo(active = it)
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun YourActiveItemFullInfo(active: ActiveUi) {

    val color = if (active.isUpDirectChangePercentage)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(28.dp),
                    imageModel = active.icon,
                    contentScale = ContentScale.Crop,
                    placeHolder = painterResource(id = R.drawable.placeholder_loading),
                    error = painterResource(id = R.drawable.placeholder_error),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = active.abbreviated,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = active.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            RelativeLabel(up = active.isUpDirectChangePercentage, value = active.changePercentage)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.3.dp,
            color = MaterialTheme.colorScheme.hint
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = active.equivalent,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = active.portfolio,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = active.changeValue,
                    style = MaterialTheme.typography.titleSmall,
                    color = color
                )
            }
        }
    }
}

@Composable
fun PortfolioInfoWithTotalReturn(
    balancePortfolio: String,
    totalReturn: String,
    changePercentageBalance: Double
) {

    val icon = if (changePercentageBalance > 0.0)
        R.drawable.home_ic_up_right
    else
        R.drawable.home_ic_down_left

    val color = if (changePercentageBalance > 0.0)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                color = MaterialTheme.colorScheme.primary,
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painterResource(id = R.drawable.home_pic_total_background),
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Row(
            modifier = Modifier.padding(
                vertical = 26.dp,
                horizontal = 24.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.home_total_portfolio),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = balancePortfolio,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Общая доходность $totalReturn",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = icon),
                    tint = color,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${"%.1f".format(abs(changePercentageBalance))}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color
                )
            }
        }
    }
}



