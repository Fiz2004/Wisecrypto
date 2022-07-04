package com.fiz.wisecrypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fiz.wisecrypto.ui.screens.AppNavHost
import com.fiz.wisecrypto.ui.screens.NamesScreen
import com.fiz.wisecrypto.ui.theme.WisecryptoTheme
import com.fiz.wisecrypto.ui.theme.isLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            val backstackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = backstackEntry?.destination

            val useDarkIcons = MaterialTheme.colorScheme.isLight()
            val systemUiController = rememberSystemUiController()

            SideEffect {

                when (currentScreen?.route) {
                    null,
                    NamesScreen.Splash.name,
                    NamesScreen.SignIn.name,
                    NamesScreen.SignUp.name,
                    NamesScreen.SignUp2.name,
                    -> {
                        systemUiController.isStatusBarVisible = false
                    }
                    else -> {
                        systemUiController.isStatusBarVisible = true
                    }
                }

                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }


            WisecryptoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(
                        navController = navController,
                    )
                }
            }
        }
    }
}