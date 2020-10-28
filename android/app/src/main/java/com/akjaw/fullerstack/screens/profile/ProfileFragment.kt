package com.akjaw.fullerstack.screens.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import coil.load
import coil.transform.CircleCropTransformation
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.authentication.model.UserProfile
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import org.kodein.di.direct
import org.kodein.di.instance

class ProfileFragment : BaseFragment(R.layout.layout_profile) {

    private val profileViewModel: ProfileViewModel by activityViewModels {
        di.direct.instance()
    }

    private lateinit var toolbar: Toolbar
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var pictureImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var logOutButton: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.profile_toolbar_title)

        loadingIndicator = view.findViewById(R.id.profile_loading_indicator)
        pictureImageView = view.findViewById(R.id.profile_image)
        nameTextView = view.findViewById(R.id.profile_name)
        emailTextView = view.findViewById(R.id.profile_email)
        logOutButton = view.findViewById(R.id.profile_logout_button)

        profileViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            populateProfileScreen(userProfile)
        }
    }

    private fun populateProfileScreen(profile: UserProfile) {
        pictureImageView.load(profile.profilePictureUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_person_24dp)
            transformations(CircleCropTransformation())
            listener(
                onError = { _, _ -> fadeInContent() },
                onSuccess = { _, _ -> fadeInContent() }
            )
        }
        nameTextView.text = profile.name
        if (profile.name != profile.email) {
            emailTextView.text = profile.email
        }

        logOutButton.setOnClickListener {
            profileViewModel.signOut(activity!!)
        }
    }

    private fun fadeInContent() {
        loadingIndicator.visibility = View.GONE
        val content = listOf(pictureImageView, nameTextView, emailTextView, logOutButton)
        val isContentHidden = content.none { it.isVisible }
        if (isContentHidden) {
            content.forEach {
                it.fadeIn()
            }
        }
    }

    private fun View.fadeIn() {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(null)
    }
}
