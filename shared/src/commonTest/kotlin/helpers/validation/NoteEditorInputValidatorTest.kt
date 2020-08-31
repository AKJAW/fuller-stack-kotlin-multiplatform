package helpers.validation

import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class NoteEditorInputValidatorTest {

    private lateinit var SUT: NoteEditorInputValidator

    @BeforeTest
    fun setUp() {
        SUT = NoteEditorInputValidator()
    }

    @JsName("TitleLongerThan30IsInvalid")
    @Test
    fun `A title longer than 30 is invalid`() {
        val title = "K".repeat(31)

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Invalid)
    }

    @JsName("BlankTitleIsInvalid")
    @Test
    fun `A blank title is invalid`() {
        val title = ""

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Invalid)
    }

    @JsName("ATitleWithLengthBetween1and30IsValid")
    @Test
    fun `A title with length between 1 and 30 is valid`() {
        val title = "An interesting title"

        val result = SUT.isTitleValid(title)

        assertTrue(result is NoteInputValidator.ValidationResult.Valid)
    }
}
