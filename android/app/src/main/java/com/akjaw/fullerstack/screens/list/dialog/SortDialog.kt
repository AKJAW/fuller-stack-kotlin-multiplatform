package com.akjaw.fullerstack.screens.list.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.list.NotesListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import feature.local.sort.SortDirection
import feature.local.sort.SortProperty
import feature.local.sort.SortType
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.direct
import org.kodein.di.instance

class SortDialog : DialogFragment(), DIAware {

    companion object {
        fun newInstance(): SortDialog {
            return SortDialog()
        }
    }

    override val di: DI by di()
    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    private lateinit var rootView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw IllegalStateException("Activity is null")

        rootView = layoutInflater.inflate(R.layout.view_sort_radio, null)
        val builder = MaterialAlertDialogBuilder(activity, R.style.AppTheme_AlertDialogTheme)
        builder.setView(rootView)

        return builder.create().apply {
            //TODO view depending on selection
            rootView.findViewById<RadioGroup>(R.id.group_sort_by)?.let { sortByGroup ->
                val id = when (viewModel.sortProperty.type) {
                    SortType.CREATION_DATE -> R.id.sort_by_creation_date
                    SortType.NAME -> R.id.sort_by_name
                }
                sortByGroup.findViewById<RadioButton>(id).isChecked = true
                sortByGroup.setOnSortPropertyChangedListener()
            }
            rootView.findViewById<RadioGroup>(R.id.group_sort_direction)?.let { directionGroup ->
                val id = when (viewModel.sortProperty.direction) {
                    SortDirection.ASCENDING -> R.id.sort_direction_ascending
                    SortDirection.DESCENDING -> R.id.sort_direction_descending
                }
                directionGroup.findViewById<RadioButton>(id).isChecked = true
                directionGroup.setOnSortPropertyChangedListener()
            }
        }
    }

    private fun RadioGroup.setOnSortPropertyChangedListener() {
        setOnCheckedChangeListener { radioGroup, checkedId ->
            val radioButton = radioGroup.findViewById<RadioButton>(checkedId) ?: return@setOnCheckedChangeListener
            val newSortProperty = getSortProperty(radioButton.id)
            viewModel.changeSortProperty(newSortProperty)
        }
    }

    private fun getSortProperty(@IdRes radioButtonId: Int): SortProperty {
        val currentSortProperty = viewModel.sortProperty
        return when (radioButtonId) {
            R.id.sort_by_creation_date -> currentSortProperty.copy(type = SortType.CREATION_DATE)
            R.id.sort_by_name -> currentSortProperty.copy(type = SortType.NAME)
            R.id.sort_direction_ascending -> currentSortProperty.copy(direction = SortDirection.ASCENDING)
            R.id.sort_direction_descending -> currentSortProperty.copy(direction = SortDirection.DESCENDING)
            else -> currentSortProperty
        }
    }
}
