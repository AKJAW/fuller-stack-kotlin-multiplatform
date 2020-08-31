package helpers.validation

interface NoteInputValidator {

    sealed class ValidationResult {

        object Valid : ValidationResult()

        class Invalid(val errorMessage: String) : ValidationResult()
    }

    fun isTitleValid(title: String): ValidationResult
}
