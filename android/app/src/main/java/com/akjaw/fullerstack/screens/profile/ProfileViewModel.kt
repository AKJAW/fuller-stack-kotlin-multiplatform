package com.akjaw.fullerstack.screens.profile

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.akjaw.fullerstack.authentication.GetUserProfile
import com.akjaw.fullerstack.authentication.UserAuthenticator
import com.akjaw.fullerstack.authentication.model.UserProfile
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userAuthenticationManager: UserAuthenticator,
    private val getUserProfile: GetUserProfile
) : ViewModel() {

    val userProfile: LiveData<UserProfile> = liveData(viewModelScope.coroutineContext) {
        val profile = getUserProfile() ?: UserProfile()
        this.emit(profile)
    }

    fun signOut(activity: Activity) = viewModelScope.launch{
        userAuthenticationManager.signOutUser(activity)
    }
}
