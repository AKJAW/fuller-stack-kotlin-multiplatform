package helpers.validation

interface NoteInputValidator {

    //TODO make a string provider for both android and react
    sealed class ValidationResult {

        object Valid: ValidationResult()

        class Invalid(errorMessage: String)
    }

    fun isTitleValid(title: String): Boolean

}
