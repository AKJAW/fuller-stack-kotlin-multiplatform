package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import database.NoteEntity
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteSchema
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

class UpdateNoteTest : FunSpec({

    val date = DateTime.createAdjusted(2020, 7, 28)
    val INITIAL_NOTE = Note(
        title = "title",
        content = "content",
        lastModificationTimestamp = date.unixMillisLong.toLastModificationTimestamp(),
        creationTimestamp = date.unixMillisLong.toCreationTimestamp()
    )
    val UPDATED_TITLE = "Updated title"
    val UPDATED_CONTENT = "Updated content"

    lateinit var unixTimestampProviderFake: UnixTimestampProviderFake
    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    lateinit var SUT: UpdateNote

    beforeTest {
        unixTimestampProviderFake = UnixTimestampProviderFake()
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = UpdateNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            unixTimestampProvider = unixTimestampProviderFake,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.initializeNoteEntities(listOf(INITIAL_NOTE))
        noteApiTestFake.initializeSchemas(listOf(INITIAL_NOTE))
    }

    suspendingTest("When the API call is successful then return true") {
        val result = SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        result.shouldBeTrue()
    }

    suspendingTest("When the API call fails then return false") {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        result.shouldBeFalse()
    }

    suspendingTest("Note is updated in the local database") {
        val lastModificationTimestamp = 50L
        unixTimestampProviderFake.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = NoteEntity(
            localId = -1,
            title = UPDATED_TITLE,
            content = UPDATED_CONTENT,
            lastModificationTimestamp = lastModificationTimestamp.toLastModificationTimestamp(),
            creationTimestamp = INITIAL_NOTE.creationTimestamp
        )

        noteDaoTestFake.notes.first() shouldBe expectedNote
    }

    suspendingTest("Note is updated in the API") {
        val lastModificationTimestamp = 50L
        unixTimestampProviderFake.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = NoteSchema(
            apiId = -1,
            title = UPDATED_TITLE,
            content = UPDATED_CONTENT,
            lastModificationTimestamp = lastModificationTimestamp.toLastModificationTimestamp(),
            creationTimestamp = INITIAL_NOTE.creationTimestamp
        )
        noteApiTestFake.notes.first() shouldBe expectedNote
    }

    suspendingTest("When API request fails then sync failed is set") {
        noteApiTestFake.willFail = true

        SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        noteDaoTestFake.notes.first().hasSyncFailed.shouldBeTrue()
    }

    suspendingTest("When API request succeeds then hasSyncFailed is updated") {
        noteDaoTestFake.initializeNoteEntities(listOf(INITIAL_NOTE.copy(hasSyncFailed = true)))
        noteApiTestFake.initializeSchemas(listOf(INITIAL_NOTE))

        SUT.executeAsync(INITIAL_NOTE.creationTimestamp, UPDATED_TITLE, UPDATED_CONTENT)

        println(noteDaoTestFake.notes)
        noteDaoTestFake.notes.first().hasSyncFailed.shouldBeFalse()
    }
})
