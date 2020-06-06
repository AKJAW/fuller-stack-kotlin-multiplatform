package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import network.NetworkApiFake
import runTest
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FetchNotesListUseCaseAsyncTest {

    private lateinit var noteApiFake : NetworkApiFake
    private lateinit var SUT : FetchNotesListUseCaseAsync

    @BeforeTest
    fun setUp(){
        noteApiFake = NetworkApiFake()
        SUT = FetchNotesListUseCaseAsync(noteApiFake)
    }

    @JsName("callsTheApiOnce")
    @Test
    fun `executeAsync calls the Api once`() = runTest {
        SUT.executeAsync(UseCaseAsync.None()) {}
        assertEquals(1, noteApiFake.callCount)
    }

    @JsName("callsTheCallbackWithList")
    @Test
    fun `executeAsync success calls the callback with the list`() = runTest {
        val expectedList = listOf(Note("a"), Note("b"))
        noteApiFake.notes = expectedList
        SUT.executeAsync(UseCaseAsync.None()) {
            val result = it as Either.Right
            assertEquals(expectedList, result.r)
        }
    }

    @JsName("callsTheCallbackWithFailure")
    @Test
    fun `executeAsync failure calls the callback with ServerError`() = runTest {
        noteApiFake.willFail = true
        SUT.executeAsync(UseCaseAsync.None()) {
            val result = it as Either.Left
            assertEquals(Failure.ServerError, result.l)
        }
    }
}
