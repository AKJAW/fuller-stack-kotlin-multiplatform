package feature.noteslist

import model.Note
import network.NetworkApiFake
import repository.NoteRepositoryTestFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddNoteTest {

    lateinit var networkApiFake: NetworkApiFake
    lateinit var noteRepositoryTestFake: NoteRepositoryTestFake
    lateinit var SUT: AddNote

    @BeforeTest
    fun setUp() {
        networkApiFake = NetworkApiFake()
        noteRepositoryTestFake = NoteRepositoryTestFake(networkApiFake)
        SUT = AddNote(noteRepositoryTestFake)
    }

    @JsName("passesTheNoteToTheRepositoryAddNote")
    @Test
    fun `executeAsync passes the note to the repository addNote`() = runTest {
        val note = Note(id = -1, title = "testing")
        SUT.executeAsync(note)
        assertEquals(note, noteRepositoryTestFake.addedNotes[0])
        assertEquals(1, noteRepositoryTestFake.addNoteCallCount)
    }
}
