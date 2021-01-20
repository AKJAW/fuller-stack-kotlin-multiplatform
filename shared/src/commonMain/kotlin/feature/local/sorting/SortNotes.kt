package feature.local.sorting

import model.Note

class SortNotes {

    fun execute(notes: List<Note>, sortProperty: SortProperty): List<Note> {
        return notes.sortedWith(NotesComparator(sortProperty))
    }

    private class NotesComparator(private val sortProperty: SortProperty) : Comparator<Note> {
        override fun compare(a: Note, b: Note): Int {
            return when (sortProperty) {
                is SortProperty.Name -> {
                    if (sortProperty.type == SortType.ASCENDING) {
                        compareValues(a.title, b.title)
                    } else {
                        compareValues(b.title, a.title)
                    }
                }
                is SortProperty.CreationDate -> {
                    if (sortProperty.type == SortType.ASCENDING) {
                        compareValues(a.creationTimestamp.unix, b.creationTimestamp.unix)
                    } else {
                        compareValues(b.creationTimestamp.unix, a.creationTimestamp.unix)
                    }
                }
            }
        }
    }
}
