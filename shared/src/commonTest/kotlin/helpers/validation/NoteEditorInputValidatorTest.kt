package helpers.validation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should

class NoteEditorInputValidatorTest : FunSpec ({

    lateinit var SUT: NoteEditorInputValidator

    beforeTest {
        SUT = NoteEditorInputValidator()
    }

    test("A title longer than 30 is invalid") {
        val title = "K".repeat(31)

        val result = SUT.isTitleValid(title)

        result.should { it is NoteInputValidator.ValidationResult.Invalid }
    }

    test("A blank title is invalid") {
        val title = ""

        val result = SUT.isTitleValid(title)

        result.should { it is NoteInputValidator.ValidationResult.Invalid }
    }

    test("A title with length between 1 and 30 is valid") {
        val title = "An interesting title"

        val result = SUT.isTitleValid(title)

        result.should { it is NoteInputValidator.ValidationResult.Valid }
    }
})
