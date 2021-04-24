package feature.local.sort

data class SortProperty(
    val type: SortType,
    val direction: SortDirection
) {
    companion object {
        val DEFAULT = SortProperty(SortType.CREATION_DATE, SortDirection.DESCENDING)
    }
}
