package com.fiz.wisecrypto.ui.screens.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.components.Progress
import com.fiz.wisecrypto.ui.theme.BlueForGradient
import com.fiz.wisecrypto.ui.theme.GreenForGradient
import com.fiz.wisecrypto.ui.theme.White
import com.fiz.wisecrypto.ui.theme.WisecryptoTheme

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel()
) {
    val state = viewModel.viewState

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(SplashEvent.StartScreen)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    start = Offset(188f, -26.5f),
                    end = Offset(188f, 1352.5f),
                    colors = listOf(GreenForGradient, BlueForGradient)
                )
            )
            .padding(horizontal = 48.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(270.dp, 95.dp),
            painter = painterResource(R.drawable.splash_pic_logo),
            contentDescription = null
        )

        Text(
            text = stringResource(R.string.splash_title),
            color = White,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.weight(0.5f))

        if (state.isLoading)
            Progress(modifier = Modifier.align(CenterHorizontally))

        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 375,
    heightDp = 812
)
@Composable
private fun DefaultPreview() {
    WisecryptoTheme {
        SplashScreen()
    }
}