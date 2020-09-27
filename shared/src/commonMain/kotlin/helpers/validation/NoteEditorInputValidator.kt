package helpers.validation

import helpers.validation.NoteInputValidator.ValidationResult.Invalid
import helpers.validation.NoteInputValidator.ValidationResult.Valid

class NoteEditorInputValidator : NoteInputValidator {

    companion object {
        private const val MAX_TITLE_LENGTH = 30
    }

    override fun isTitleValid(title: String): NoteInputValidator.ValidationResult {
        return when {
            title.count() > MAX_TITLE_LENGTH -> Invalid("Title is too long")
            title.isBlank() -> Invalid("Title can't be empty")
            else -> Valid
        }
    }
}
