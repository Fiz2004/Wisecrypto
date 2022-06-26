package com.fiz.wisecrypto.ui.screens.splash

sealed class SplashViewEffect {
    object MoveSignInScreen : SplashViewEffect()
    object MoveMainContentScreen : SplashViewEffect()
}