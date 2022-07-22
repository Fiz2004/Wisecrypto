package com.fiz.wisecrypto.ui.screens.main.home.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun RelativeLabel(
    Increased: Boolean,
    value: String
) {

    val icon = if (Increased)
        R.drawable.home_ic_up_right
    else
        R.drawable.home_ic_down_left

    val colorBackground = if (Increased)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.error

    Row(
        modifier = Modifier
            .background(
                color = colorBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painterResource(id = icon),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}