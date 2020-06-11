package feature.noteslist

import base.usecase.UseCaseAsync
import network.NetworkApiFake
import repository.NoteRepositoryFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshNotesTest {

    lateinit var networkApiFake: NetworkApiFake
    lateinit var noteRepositoryFake: NoteRepositoryFake
    lateinit var SUT: RefreshNotes

    @BeforeTest
    fun setUp(){
        networkApiFake = NetworkApiFake()
        noteRepositoryFake = NoteRepositoryFake(networkApiFake)
        SUT = RefreshNotes(noteRepositoryFake)
    }

    @JsName("callsTheRepositoryRefreshNotesOnce")
    @Test
    fun `executeAsync passes the note to the repository refreshNotes`() = runTest {
        SUT.executeAsync(UseCaseAsync.None())
        assertEquals(1, noteRepositoryFake.refreshNotesCallCount)
    }
}
