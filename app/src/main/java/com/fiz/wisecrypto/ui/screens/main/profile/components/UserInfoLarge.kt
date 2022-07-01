package com.fiz.wisecrypto.ui.screens.main.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.theme.displayLarge2

@Composable
fun UserInfoLarge() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(40.dp)),
            painter = painterResource(id = R.drawable.pic_avatar_test),
            contentDescription = null
        )
        Text(
            text = "Test Test",
            style = MaterialTheme.typography.displayLarge2
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}