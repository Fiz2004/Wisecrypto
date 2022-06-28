package com.fiz.wisecrypto.ui.screens.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fiz.wisecrypto.R

@Composable
fun YourActive() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            YourActiveItem(
                icon = R.drawable.home_pic_bitcoin,
                abbreviated = "BTC",
                name = "Bitcoin",
                portfolio = "\$26.46",
                equivalent = "0.0012 BTC",
                up = true,
                value = "15,3%"
            )
        }

        item {
            YourActiveItem(
                icon = R.drawable.home_pic_etherium,
                abbreviated = "ETH",
                name = "Etherium",
                portfolio = "\$37.30",
                equivalent = "0.009 BTC",
                up = false,
                value = "-2,1%"
            )
        }
    }
}