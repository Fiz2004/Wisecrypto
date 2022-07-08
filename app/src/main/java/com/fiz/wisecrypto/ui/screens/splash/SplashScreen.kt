package com.fiz.wisecrypto.ui.screens.splash

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiz.wisecrypto.ui.screens.splash.components.Logo
import com.fiz.wisecrypto.ui.screens.splash.components.ProgressOrSpacer
import com.fiz.wisecrypto.ui.screens.splash.components.SplashColumn
import com.fiz.wisecrypto.ui.screens.splash.components.TextSlogan

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(),
    moveSignInScreen: () -> Unit = {},
    moveMainContentScreen: () -> Unit = {}
) {
    val viewState = viewModel.viewState
    val viewEffect = viewModel.viewEffect

    LaunchedEffect(Unit) {

        viewModel.onEvent(SplashEvent.StartScreen)

        viewEffect.collect { effect ->
            when (effect) {
                SplashViewEffect.MoveMainContentScreen -> moveMainContentScreen()
                SplashViewEffect.MoveSignInScreen -> moveSignInScreen()
            }
        }
    }

    SplashColumn {
        Spacer(modifier = Modifier.weight(1f))
        Logo()
        TextSlogan()
        Spacer(modifier = Modifier.weight(0.5f))
        ProgressOrSpacer(viewState.isLoading, modifier = Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

