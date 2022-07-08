package com.fiz.wisecrypto.ui.screens.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.ui.theme.BlueForGradient
import com.fiz.wisecrypto.ui.theme.GreenForGradient

@Composable
fun SplashColumn(content: @Composable ColumnScope.() -> Unit) {
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
        content()
    }
}