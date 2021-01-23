package feature.local.sort

import model.Note

class SortNotes {

    fun execute(notes: List<Note>, sortProperty: SortProperty): List<Note> {
        return notes.sortedWith(NotesComparator(sortProperty))
    }

    private class NotesComparator(private val sortProperty: SortProperty) : Comparator<Note> {
        override fun compare(a: Note, b: Note): Int {
            return when (sortProperty.type) {
                SortType.NAME -> {
                    if (sortProperty.direction == SortDirection.ASCENDING) {
                        compareValues(a.title, b.title)
                    } else {
                        compareValues(b.title, a.title)
                    }
                }
                SortType.CREATION_DATE -> {
                    if (sortProperty.direction == SortDirection.ASCENDING) {
                        compareValues(a.creationTimestamp.unix, b.creationTimestamp.unix)
                    } else {
                        compareValues(b.creationTimestamp.unix, a.creationTimestamp.unix)
                    }
                }
            }
        }
    }
}
