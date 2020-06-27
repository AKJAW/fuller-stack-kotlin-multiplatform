package feature.noteslist

import base.usecase.UseCaseAsync
import network.NetworkApiFake
import repository.NoteRepositoryTestFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshNotesTest {

    lateinit var networkApiFake: NetworkApiFake
    lateinit var noteRepositoryTestFake: NoteRepositoryTestFake
    lateinit var SUT: RefreshNotes

    @BeforeTest
    fun setUp(){
        networkApiFake = NetworkApiFake()
        noteRepositoryTestFake = NoteRepositoryTestFake(networkApiFake)
        SUT = RefreshNotes(noteRepositoryTestFake)
    }

    @JsName("callsTheRepositoryRefreshNotesOnce")
    @Test
    fun `executeAsync passes the note to the repository refreshNotes`() = runTest {
        SUT.executeAsync(UseCaseAsync.None())
        assertEquals(1, noteRepositoryTestFake.refreshNotesCallCount)
    }
}
