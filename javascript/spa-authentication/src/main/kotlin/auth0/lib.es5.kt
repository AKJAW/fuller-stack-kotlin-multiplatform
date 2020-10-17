@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS"
)
package tsstdlib

external interface ErrorConstructor {
    @nativeInvoke
    operator fun invoke(message: String = definedExternally): Error
    var prototype: Error
}

external interface PromiseLike<T> {
    fun then(
        onfulfilled: ((value: T) -> dynamic)? = definedExternally,
        onrejected: ((reason: Any) -> dynamic)? = definedExternally
    ): PromiseLike<dynamic /* TResult1 | TResult2 */>
}

typealias Pick<T, K> = Any

typealias Exclude<T, U> = Any

typealias Omit<T, K> = Any
