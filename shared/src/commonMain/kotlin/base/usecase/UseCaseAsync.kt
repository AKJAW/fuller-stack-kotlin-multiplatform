package base.usecase

import base.CommonDispatchers
import kotlinx.coroutines.withContext

abstract class UseCaseAsync<in Params, out Type> where Type : Any {

    protected abstract suspend fun run(params: Params): Either<Failure, Type>

    suspend fun executeAsync(
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        withContext(CommonDispatchers.BackgroundDispatcher) {
            val result = run(params)
            withContext(CommonDispatchers.MainDispatcher) {
                onResult(result)
            }
        }
    }

    class None
}
