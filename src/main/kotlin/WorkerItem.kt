data class WorkerItem<A, R>(
    val function: (A) -> R,
    val arg: A,
    val future: FutureResult<R>,
)