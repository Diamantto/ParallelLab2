import java.util.concurrent.ConcurrentLinkedQueue

class CustomExecutor<A, R>(
    val maxNumThreads: Int
) {

    private val queue = ConcurrentLinkedQueue<WorkerItem<A, R>>()
    private var listOfThreads = listOf<Thread>()

    fun execute(function: (A) -> R, arg: A): FutureResult<R> {
        val item = WorkerItem(
            function = function, arg = arg, future = FutureResult()
        )
        queue.add(item)

        if (isThreadAvailable()) {
            val thread = Thread(WorkerThread(queue))
            listOfThreads = listOfThreads + thread
            thread.start()
        }
        return item.future
    }

    private fun isThreadAvailable(): Boolean = listOfThreads.size < maxNumThreads

    fun map(function: (A) -> R, listOfArgs: List<A>): List<FutureResult<R>> {
        return listOfArgs.map { execute(function, it) }
    }

    fun shutdown() {
        listOfThreads.forEach {
            it.join()
        }
    }
}