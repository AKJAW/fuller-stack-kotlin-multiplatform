package feature.noteslist

import data.Note
import network.NetworkApiFake
import repository.NoteRepositoryFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddNoteTest {

    lateinit var networkApiFake: NetworkApiFake
    lateinit var noteRepositoryFake: NoteRepositoryFake
    lateinit var SUT: AddNote

    @BeforeTest
    fun setUp(){
        networkApiFake = NetworkApiFake()
        noteRepositoryFake = NoteRepositoryFake(networkApiFake)
        SUT = AddNote(noteRepositoryFake)
    }

    @JsName("passesTheNoteToTheRepositoryAddNote")
    @Test
    fun `executeAsync passes the note to the repository addNote`() = runTest {
        val note = Note("testing")
        SUT.executeAsync(note)
        assertEquals(note, noteRepositoryFake.addedNotes[0])
        assertEquals(1, noteRepositoryFake.addNoteCallCount)
    }
}
