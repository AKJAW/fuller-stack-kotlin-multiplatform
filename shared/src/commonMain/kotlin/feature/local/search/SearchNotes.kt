package feature.local.search

import model.Note

class SearchNotes {

    fun execute(notes: List<Note>, searchKeyword: String): List<Note> {
        val searchKeywordLower = searchKeyword.toLowerCase()
        return notes.filter { note ->
            note.title.toLowerCase().contains(searchKeywordLower)
        }
    }
}
