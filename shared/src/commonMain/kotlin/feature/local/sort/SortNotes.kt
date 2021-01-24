package feature.local.sort

import model.Note

class SortNotes {

    fun execute(notes: List<Note>, sortProperty: SortProperty): List<Note> {
        return notes.sortedWith(NotesComparator(sortProperty))
    }

    private class NotesComparator(private val sortProperty: SortProperty) : Comparator<Note> {
        override fun compare(a: Note, b: Note): Int {
            return when (sortProperty.direction) {
                SortDirection.ASCENDING -> when (sortProperty.type) {
                    SortType.NAME -> compareValues(a.title, b.title)
                    SortType.CREATION_DATE -> compareValues(a.creationTimestamp.unix, b.creationTimestamp.unix)
                }
                SortDirection.DESCENDING -> when (sortProperty.type) {
                    SortType.NAME -> compareValues(b.title, a.title)
                    SortType.CREATION_DATE -> compareValues(b.creationTimestamp.unix, a.creationTimestamp.unix)
                }
            }
        }
    }
}
