package helpers.validation

import io.kotest.core.spec.style.FunSpec
import suspendingTest
import kotlin.test.assertTrue

class NoteEditorInputValidatorTest : FunSpec ({

    lateinit var SUT: NoteEditorInputValidator

    beforeTest {
        SUT = NoteEditorInputValidator()
    }

    suspendingTest("A title longer than 30 is invalid") {
        val title = "K".repeat(31)

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Invalid)
    }

    suspendingTest("A blank title is invalid") {
        val title = ""

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Invalid)
    }

    suspendingTest("A title with length between 1 and 30 is valid") {
        val title = "An interesting title"

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Valid)
    }
})
