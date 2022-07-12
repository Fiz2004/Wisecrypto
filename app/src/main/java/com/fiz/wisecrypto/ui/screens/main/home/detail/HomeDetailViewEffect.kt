package com.fiz.wisecrypto.ui.screens.main.home.detail

sealed class HomeDetailViewEffect {
    object MoveReturn : HomeDetailViewEffect()
    data class ShowError(val message: String?) : HomeDetailViewEffect()
}