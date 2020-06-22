package helpers.validation

class NoteEditorInputValidator : NoteInputValidator {

    companion object {
        private const val MAX_TITLE_LENGTH = 30
    }

    override fun isTitleValid(title: String): Boolean {
        return when {
            title.count() > MAX_TITLE_LENGTH -> false
            title.isBlank() -> false
            else -> true
        }
    }

}
