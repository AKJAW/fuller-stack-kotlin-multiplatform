package base

sealed class Either<out L, out R> {

    data class Left<out L>(val l: L) : Either<L, Nothing>()

    data class Right<out R>(val r: R) : Either<Nothing, R>()

    val isRight: Boolean
        get() = this is Right<R>

    val isLeft: Boolean
        get() = this is Left<L>

    fun <L> left(l: L) = Left(l)

    fun <R> right(r: R) = Left(r)

    fun fold(leftFunction: (L) -> Any, rightFunction: (R) -> Any): Any =
        when (this) {
            is Left -> leftFunction(this.l)
            is Right -> rightFunction(this.r)
        }
}
