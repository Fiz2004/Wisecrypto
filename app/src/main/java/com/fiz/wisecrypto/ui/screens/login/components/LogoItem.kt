package com.fiz.wisecrypto.ui.screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun LogoItem() {
    Spacer(modifier = Modifier.height(32.dp))

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .size(182.dp, 64.dp),
        painter = painterResource(R.drawable.login_pic_logo),
        contentDescription = null
    )

    Spacer(
        modifier = Modifier
            .height(72.dp)
    )
}