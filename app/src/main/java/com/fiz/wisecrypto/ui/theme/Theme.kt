package com.fiz.wisecrypto.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    onPrimary = White,
    surface = Light,
    onSurface = Black,
    onSurfaceVariant = Gray,
    secondary = PurpleGrey80,
    tertiary = Warning
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    onPrimary = White,
    surface = Light,
    onSurface = Black,
    onSurfaceVariant = Gray,
    secondary = PurpleGrey40,
    tertiary = Warning

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@get:Composable
val ColorScheme.veryLightPrimary: Color
    get() = if (isLight) VeryLightGreen else VeryLightGreen

@get:Composable
val ColorScheme.hint: Color
    get() = if (isLight) Gray2 else Gray2

@get:Composable
val ColorScheme.borderCheckedBox: Color
    get() = if (isLight) Blue else Blue

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5

var isLight: Boolean = false

@Composable
fun WisecryptoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    isLight = MaterialTheme.colorScheme.isLight()

    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}