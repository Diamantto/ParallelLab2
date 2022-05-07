import java.util.concurrent.ConcurrentLinkedQueue

class WorkerThread<A, R>(
    private val queue: ConcurrentLinkedQueue<WorkerItem<A, R>>
) : Runnable {

    override fun run() {
        var item = queue.poll()
        while (item != null) {
            item.future.result = item.function(item.arg)
            item = queue.poll()
        }
    }
}