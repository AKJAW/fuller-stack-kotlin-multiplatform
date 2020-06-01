package store

import data.Note

data class State(
    val noteList: Array<Note> = emptyArray()
)
