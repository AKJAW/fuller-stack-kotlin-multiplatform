package com.akjaw.fullerstack.screens.common.main

import androidx.lifecycle.ViewModel
import feature.socket.ListenToSocketUpdates

class MainActivityViewModel(
    private val listenToSocketUpdates: ListenToSocketUpdates
) : ViewModel() {

    fun startNotesSocket() {
        listenToSocketUpdates.listenToSocketChanges()
    }

    override fun onCleared() {
        super.onCleared()
        listenToSocketUpdates.close()
    }
}
