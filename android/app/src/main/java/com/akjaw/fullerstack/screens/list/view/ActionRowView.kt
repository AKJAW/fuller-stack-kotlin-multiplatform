package com.akjaw.fullerstack.screens.list.view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.akjaw.framework.utility.KeyboardCloser
import com.akjaw.framework.view.doAfterDistinctTextChange
import com.akjaw.framework.view.makeInvisible
import com.akjaw.framework.view.setOnAnimationEnd
import com.akjaw.framework.view.show
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.navigation.DialogManager
import com.google.android.material.textfield.TextInputLayout
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance

class ActionRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), DIAware {

    class SavedState: BaseSavedState {

        var searchInputText: String? = null

        constructor(superState: Parcelable?) : super(superState)

        constructor(source: Parcel?) : super(source) {
            searchInputText = source?.readString()
        }

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(searchInputText)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel?): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    init {
        isSaveEnabled = true
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

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState().let { superState ->
            val text = searchInput.text?.toString()
            if (text != null) {
                SavedState(superState).apply {
                    searchInputText = text
                }
            } else {
                superState
            }
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                state.searchInputText?.let { searchInputText ->
                    if (searchInputText.isNotEmpty()) {
                        searchInput.setText(searchInputText)
                        textInputLayout.show()
                        isSearchInputVisible = true
                    }
                }
            }
            else -> super.onRestoreInstanceState(state)
        }
    }

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
        textInputLayout.show()
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
            textInputLayout.makeInvisible()
            searchIcon.isClickable = true
        }
        textInputLayout.startAnimation(scaleAnimation)
    }

    @Suppress("MagicNumber")
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
