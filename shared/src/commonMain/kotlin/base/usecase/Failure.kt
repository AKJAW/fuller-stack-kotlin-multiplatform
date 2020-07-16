package base.usecase

sealed class Failure {
    object ApiError : Failure()
    object NetworkError : Failure()

    abstract class FeatureFailure : Failure()
}
