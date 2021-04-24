package feature.local.search

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import model.Note

class SearchNotesTest : FunSpec({

    lateinit var SUT: SearchNotes

    val notes = listOf(
        Note(title = "A title containing"),
        Note(title = "Some other title"),
        Note(title = "Other is good")
    )

    beforeTest {
        SUT = SearchNotes()
    }

    test("Searches correctly") {
            io.kotest.data.forAll(
                table(
                    headers("search keyword", "expected notes"),
                    row("other", listOf(notes[1], notes[2])),
                    row("OTHER", listOf(notes[1], notes[2])),
                    row("title", listOf(notes[0], notes[1])),
                    row(" ", listOf(notes[0], notes[1], notes[2])),
                    row("e", listOf(notes[0], notes[1], notes[2]))

                )
            ) { sortType, expectedOrder ->
                SUT.execute(notes, sortType) shouldBe expectedOrder
            }
    }
})
