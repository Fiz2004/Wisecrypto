package com.fiz.wisecrypto.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiz.wisecrypto.ui.screens.login.signin.SignInScreen
import com.fiz.wisecrypto.ui.screens.login.signin.SignInViewModel
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpScreen
import com.fiz.wisecrypto.ui.screens.login.signup.SignUpViewModel
import com.fiz.wisecrypto.ui.screens.login.signup2.SignUp2Screen
import com.fiz.wisecrypto.ui.screens.login.signup2.SignUp2ViewModel
import com.fiz.wisecrypto.ui.screens.main.MainScreen
import com.fiz.wisecrypto.ui.screens.main.MainViewModel
import com.fiz.wisecrypto.ui.screens.splash.SplashScreen
import com.fiz.wisecrypto.ui.screens.splash.SplashViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NamesScreen.Splash.name,
    ) {
        composable(NamesScreen.Splash.name) {
            val viewModel = hiltViewModel<SplashViewModel>()

            SplashScreen(
                viewModel = viewModel,
                moveNextScreen = { navController.navigate(NamesScreen.SignIn.name) }
            )
        }
        composable(NamesScreen.SignIn.name) {
            val viewModel = hiltViewModel<SignInViewModel>()

            SignInScreen(
                viewModel = viewModel,
                moveForgotPasswordScreen = {},
                moveSignUpScreen = { navController.navigate(NamesScreen.SignUp.name) },
                moveMainContentScreen = { navController.navigate(NamesScreen.Main.name) },
            )
        }
        composable(NamesScreen.SignUp.name) {
            val viewModel = hiltViewModel<SignUpViewModel>()

            SignUpScreen(
                viewModel = viewModel,
                moveSignInScreen = { navController.navigate(NamesScreen.SignIn.name) },
                moveSignUpNextScreen = { navController.navigate(NamesScreen.SignUp2.name) },
            )
        }
        composable(NamesScreen.SignUp2.name) {
            val viewModel = hiltViewModel<SignUp2ViewModel>()
            val viewModelSignUp = hiltViewModel<SignUpViewModel>()

            SignUp2Screen(
                viewModel = viewModel,
                signUpViewState = viewModelSignUp.viewState,
                moveSignInScreen = { navController.navigate(NamesScreen.SignIn.name) },
                showTermsAndConditions = { },
                showPrivacyPolicy = { },
                showContentPolicy = { },
            )
        }
        composable(NamesScreen.Main.name) {
            val viewModel = hiltViewModel<MainViewModel>()

            MainScreen(
                viewModel = viewModel,
            )
        }
    }
}

enum class NamesScreen(name: String) {
    Splash("Splash"),
    SignIn("SignIn"),
    SignUp("SignUp"),
    SignUp2("SignUp2"),
    Main("Main"),
}