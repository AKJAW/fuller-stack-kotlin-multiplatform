package com.akjaw.fullerstack.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.akjaw.fullerstack.authentication.UserAuthenticationManager
import com.akjaw.fullerstack.authentication.model.UserProfile

class ProfileViewModel(
    private val userAuthenticationManager: UserAuthenticationManager
) : ViewModel() {

    val userProfile: LiveData<UserProfile> = liveData(viewModelScope.coroutineContext) {
        val profile = userAuthenticationManager.getUserProfile() ?: UserProfile()
        this.emit(profile)
    }

    fun logOut() {
        TODO()
    }
}
