package feature

import base.CommonDispatchers
import database.NoteEntity
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteSchema
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

class AddNoteTest : FunSpec({

    val TIMESTAMP = 70L
    val TITLE = "title"
    val CONTENT = "content"

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    lateinit var unixTimestampProviderFake: UnixTimestampProviderFake
    lateinit var SUT: AddNote

    beforeTest {
        unixTimestampProviderFake = UnixTimestampProviderFake()
        unixTimestampProviderFake.timestamp = TIMESTAMP
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = AddNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake,
            unixTimestampProvider = unixTimestampProviderFake
        )
    }

    suspendingTest("When the API call is successful then return true") {
        val result = SUT.executeAsync(TITLE, CONTENT)

        result.shouldBeTrue()
    }

    suspendingTest("When the API call fails then return false") {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(TITLE, CONTENT)

        result.shouldBeFalse()
    }

    suspendingTest("Adds the note to the local database") {
        SUT.executeAsync(TITLE, CONTENT)

        val expectedNote = NoteEntity(
            localId = 0,
            title = TITLE,
            content = CONTENT,
            lastModificationTimestamp = TIMESTAMP.toLastModificationTimestamp(),
            creationTimestamp = TIMESTAMP.toCreationTimestamp()
        )
        noteDaoTestFake.notes.first() shouldBe expectedNote
    }

    suspendingTest("Adds the note to the API") {
        SUT.executeAsync(TITLE, CONTENT)

        val expectedNote = NoteSchema(
            apiId = 0,
            title = TITLE,
            content = CONTENT,
            lastModificationTimestamp = TIMESTAMP.toLastModificationTimestamp(),
            creationTimestamp = TIMESTAMP.toCreationTimestamp()
        )
        noteApiTestFake.notes.first() shouldBe expectedNote
    }

    suspendingTest("When request fails then set sync failed in the local database") {
        noteApiTestFake.willFail = true

        SUT.executeAsync(TITLE, CONTENT)

        noteDaoTestFake.notes.first().hasSyncFailed.shouldBeTrue()
    }
})
