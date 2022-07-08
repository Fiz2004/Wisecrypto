package com.fiz.wisecrypto.ui.screens.splash.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun Logo() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .size(272.dp, 96.dp),
        painter = painterResource(R.drawable.splash_pic_logo),
        contentDescription = null
    )
}