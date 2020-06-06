package base

sealed class Failure {
    object ServerError : Failure()

    abstract class FeatureFailure : Failure()
}
