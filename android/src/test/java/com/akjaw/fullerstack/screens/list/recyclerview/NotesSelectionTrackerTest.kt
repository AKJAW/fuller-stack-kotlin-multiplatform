package com.akjaw.fullerstack.screens.list.recyclerview

import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import model.NoteIdentifier
import model.NoteIdentifierMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class NotesSelectionTrackerTest {

    private val noteIdentifierMapper = NoteIdentifierMapper()
    private val fragmentManager: FragmentManager = mockk(relaxed = true)
    private val notesListActionMode: NotesListActionMode = mockk {
        every { initialize(any(), any()) } answers {}
        every { startActionMode() } answers {}
        every { exitActionMode() } answers {}
        every { setTitle(any()) } answers {}
    }
    private val onNoteChanged: (NoteIdentifier) -> Unit = mockk(relaxed = true)
    private lateinit var SUT: NotesSelectionTracker

    @Nested
    inner class EmptyInitialSelection {

        @BeforeEach
        fun setUp() {
            SUT = NotesSelectionTracker(
                listOf(),
                fragmentManager,
                noteIdentifierMapper,
                notesListActionMode,
                onNoteChanged
            )
        }

        @Test
        fun `SUT initialization also calls the action mode initialization`() {
            verify(exactly = 1) {
                notesListActionMode.initialize(any(), any())
            }
        }

        @Test
        fun `Selecting notes toggles between selected and not selected`() {
            SUT.select(NoteIdentifier(1))
            SUT.select(NoteIdentifier(2))
            SUT.select(NoteIdentifier(3))

            SUT.select(NoteIdentifier(1))
            SUT.select(NoteIdentifier(3))
            SUT.select(NoteIdentifier(3))

            val selectedNotes = SUT.getSelectedIds()
            assertEquals(listOf(2, 3), selectedNotes)
        }

        @Test
        fun `Selecting notes calls the correct callback`() {
            SUT.select(NoteIdentifier(2))
            SUT.select(NoteIdentifier(2))
            SUT.select(NoteIdentifier(2))
            SUT.select(NoteIdentifier(3))

            verify(exactly = 3) {
                onNoteChanged.invoke(NoteIdentifier(2))
            }
            verify(exactly = 1) {
                onNoteChanged.invoke(NoteIdentifier(3))
            }
        }

        @Test
        fun `Selecting notes changes the action mode title`() {
            SUT.select(NoteIdentifier(1))
            SUT.select(NoteIdentifier(2))

            verify(exactly = 1) {
                notesListActionMode.setTitle("1")
            }

            verify(exactly = 1) {
                notesListActionMode.setTitle("2")
            }
        }

        @Test
        fun `Selecting an initial note starts the action mode`() {
            SUT.select(NoteIdentifier(1))

            verify(exactly = 1) {
                notesListActionMode.startActionMode()
            }
        }

        @Test
        fun `Unselecting the last note exits the action mode`() {
            SUT.select(NoteIdentifier(1))

            SUT.select(NoteIdentifier(1))

            verify(exactly = 1) {
                notesListActionMode.exitActionMode()
            }
        }
    }

    @Nested
    inner class InitialSelectionProvided {
        @BeforeEach
        fun setUp() {
            SUT = NotesSelectionTracker(
                listOf(NoteIdentifier(1), NoteIdentifier(2)),
                fragmentManager,
                noteIdentifierMapper,
                notesListActionMode,
                onNoteChanged
            )
        }

        @Test
        fun `Initialization open the action mode if there are initial selected notes`() {
            verify(exactly = 1) {
                notesListActionMode.startActionMode()
            }
        }

        @Test
        fun `Selected notes are initialized from the constructor`() {
            val selectedNotes = SUT.getSelectedIds()

            assertEquals(listOf(1, 2), selectedNotes)
        }
    }
}
