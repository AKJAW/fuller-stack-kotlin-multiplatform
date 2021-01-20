package feature.local.sort

sealed class SortProperty(val type: SortType) {
    class Name(type: SortType): SortProperty(type)
    class CreationDate(type: SortType): SortProperty(type)
}
