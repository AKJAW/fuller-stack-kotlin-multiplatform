package model

class NoteIdentifierMapper {

    fun toInt(identifiers: List<NoteIdentifier>): List<Int> = identifiers.map { it.id }

    fun toIdentifiers(ids: List<Int>): List<NoteIdentifier> = ids.map { NoteIdentifier(it) }
}
