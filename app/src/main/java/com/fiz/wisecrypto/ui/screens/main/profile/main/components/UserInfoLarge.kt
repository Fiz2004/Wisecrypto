package com.fiz.wisecrypto.ui.screens.main.profile.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
fun UserInfoLarge(
    fullName: String,
    onClickChangeAvatar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(40.dp)),
                painter = painterResource(id = R.drawable.pic_avatar_test),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
                    .clickable { onClickChangeAvatar() }
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.profile_ic_change_avatar),
                contentDescription = null
            )
        }
        Text(
            text = fullName,
            style = MaterialTheme.typography.displayLarge2
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}