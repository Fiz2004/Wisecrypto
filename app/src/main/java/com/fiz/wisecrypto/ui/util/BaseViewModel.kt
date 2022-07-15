package com.fiz.wisecrypto.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiz.wisecrypto.util.Consts
import kotlinx.coroutines.*

abstract class BaseViewModel: ViewModel(), LifeCycleEventable {
    private var jobRefresh: Job? = null
    override fun started() {
        if (jobRefresh == null)
            jobRefresh = viewModelScope.launch(Dispatchers.Default) {
                while (isActive) {
                    refresh()
                    delay(Consts.TIME_REFRESH_NETWORK_MS.toLong())
                }
            }
    }

    override fun stopped() {
        viewModelScope.launch {
            jobRefresh?.cancelAndJoin()
            jobRefresh = null
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            jobRefresh?.cancelAndJoin()
            jobRefresh = null
            started()
        }
    }

    abstract suspend fun refresh()
}