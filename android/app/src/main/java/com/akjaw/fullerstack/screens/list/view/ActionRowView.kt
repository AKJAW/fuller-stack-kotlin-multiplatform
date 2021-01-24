package com.akjaw.fullerstack.screens.list.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import com.akjaw.framework.utility.KeyboardCloser
import com.akjaw.framework.view.doAfterDistinctTextChange
import com.akjaw.framework.view.setOnAnimationEnd
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.navigation.DialogManager
import com.akjaw.fullerstack.screens.list.NotesListViewModel
import com.google.android.material.textfield.TextInputLayout
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.android.x.di
import org.kodein.di.direct
import org.kodein.di.instance

class ActionRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DIAware {

    init {
        inflate(context, R.layout.view_action_row, this)
    }

    override val di: DI by di()
    private val dialogManager: DialogManager by instance()

    private val textInputLayout: TextInputLayout = rootView.findViewById(R.id.search_input_layout)
    private val searchInput: EditText = rootView.findViewById(R.id.search_input_edit_text)
    private val searchIcon: ImageView = rootView.findViewById(R.id.search_icon)
    private val sortIcon: ImageView = rootView.findViewById(R.id.sort_icon)
    private var textWatcher: TextWatcher? = null
    private var isSearchInputVisible = false

    fun initialize(
        keyboardCloser: KeyboardCloser,
        onSearchInputChange: (String) -> Unit
    ) {
        textWatcher = searchInput.doAfterDistinctTextChange { text ->
            onSearchInputChange(text)
        }

        textInputLayout.setEndIconOnClickListener {
            searchInput.setText("")
            keyboardCloser.close()
            searchInput.clearFocus()
        }

        searchIcon.setOnClickListener {
            searchIcon.isClickable = false
            if (isSearchInputVisible) {
                collapseInput()
            } else {
                expandInput()
            }
            isSearchInputVisible = !isSearchInputVisible
        }

        sortIcon.setOnClickListener {
            dialogManager.showSortDialog()
        }
    }

    fun unbind() {
        searchInput.removeTextChangedListener(textWatcher)
        textWatcher = null
    }

    private fun expandInput() {
        textInputLayout.visibility = View.VISIBLE
        val scaleAnimation = createScaleAnimation(0f, 1f)
        scaleAnimation.setOnAnimationEnd {
            searchInput.isEnabled = true
            searchIcon.isClickable = true
        }
        textInputLayout.startAnimation(scaleAnimation)
    }

    private fun collapseInput() {
        searchInput.setText("")
        searchInput.isEnabled = false
        val scaleAnimation = createScaleAnimation(1f, 0f)
        scaleAnimation.setOnAnimationEnd {
            textInputLayout.visibility = View.INVISIBLE
            searchIcon.isClickable = true
        }
        textInputLayout.startAnimation(scaleAnimation)
    }

    private fun createScaleAnimation(fromX: Float, toX: Float): ScaleAnimation {
        val scaleAnimation =
            ScaleAnimation(
                fromX,
                toX,
                1f,
                1f,
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
        scaleAnimation.duration = 300
        scaleAnimation.fillAfter = true
        return scaleAnimation
    }
}
