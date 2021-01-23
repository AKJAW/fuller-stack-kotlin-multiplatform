package feature.local.sort

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import model.CreationTimestamp
import model.Note

class SortNotesTest : FunSpec({

    lateinit var SUT: SortNotes

    val notes = listOf(
        Note(title = "1 Note", creationTimestamp = CreationTimestamp(20)),
        Note(title = "2 Note", creationTimestamp = CreationTimestamp(10)),
        Note(title = "3 Note", creationTimestamp = CreationTimestamp(15))
    )

    beforeTest {
        SUT = SortNotes()
    }

    test("Correctly sorts") {
            io.kotest.data.forAll(
                table(
                    headers("sortProperty", "expected order"),
                    row(SortProperty(SortType.NAME, SortDirection.ASCENDING), listOf(notes[0], notes[1], notes[2])),
                    row(SortProperty(SortType.NAME, SortDirection.DESCENDING), listOf(notes[2], notes[1], notes[0])),
                    row(SortProperty(SortType.CREATION_DATE, SortDirection.ASCENDING), listOf(notes[1], notes[2], notes[0])),
                    row(SortProperty(SortType.CREATION_DATE, SortDirection.DESCENDING), listOf(notes[0], notes[2], notes[1]))

                )
            ) { sortType, expectedOrder ->
                SUT.execute(notes, sortType) shouldBe expectedOrder
            }
    }
})
