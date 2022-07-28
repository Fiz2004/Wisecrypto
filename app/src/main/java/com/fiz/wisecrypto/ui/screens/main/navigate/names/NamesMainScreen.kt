package com.fiz.wisecrypto.ui.screens.main.navigate.names

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fiz.wisecrypto.R

sealed class NamesMainScreen(
    val name: String,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int
) {
    object Home : NamesMainScreen("Home", R.string.screen_home, R.drawable.bottom_nav_ic_home)

    object Market :
        NamesMainScreen("Market", R.string.screen_market, R.drawable.bottom_nav_ic_market)

    object Profile :
        NamesMainScreen("Profile", R.string.screen_profile, R.drawable.bottom_nav_ic_profile)
}