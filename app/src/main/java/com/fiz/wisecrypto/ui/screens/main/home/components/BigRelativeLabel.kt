package com.fiz.wisecrypto.ui.screens.main.home.components

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
fun BigRelativeLabel(
    inverse: Boolean,
    increased: Boolean,
    value: String
) {

    val icon = if (increased)
        R.drawable.home_ic_up_right
    else
        R.drawable.home_ic_down_left

    val colorMain = if (increased)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.secondary

    val colorOnMain = MaterialTheme.colorScheme.onPrimary

    val colorContent = if (inverse)
        colorMain
    else
        colorOnMain

    val colorBackground = if (inverse)
        colorOnMain
    else
        colorMain

    Row(
        modifier = Modifier
            .background(
                color = colorBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            tint = colorContent,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colorContent
        )
    }
}