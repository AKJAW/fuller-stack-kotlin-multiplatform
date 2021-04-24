package com.akjaw.fullerstack.screens.common.navigation

import model.CreationTimestamp

interface DialogManager {

    fun showDeleteNotesConfirmDialog(
        noteIdentifiers: List<CreationTimestamp>,
        onNotesDeleted: () -> Unit
    )

    fun showSortDialog()
}
