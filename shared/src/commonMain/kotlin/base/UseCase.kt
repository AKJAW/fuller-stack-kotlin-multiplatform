package base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCaseAsync<in Params, out Type> where Type : Any {

    protected abstract suspend fun run(params: Params) : Either<Failure, Type>

    suspend fun executeAsync(
        dispatcher: CoroutineDispatcher,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        withContext(dispatcher) {
            onResult(run(params))
        }
    }

    class None
}