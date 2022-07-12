package com.fiz.wisecrypto.ui.screens.main.home.detail

sealed class HomeDetailEvent {
    object BackButtonClicked : HomeDetailEvent()
    object AddWatchListClicked : HomeDetailEvent()
    object Started : HomeDetailEvent()
    object Stopped : HomeDetailEvent()
    data class Create(val id: String) : HomeDetailEvent()
    data class PeriodFilterChipClicked(val index: Int) : HomeDetailEvent()
}