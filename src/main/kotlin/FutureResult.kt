import java.util.concurrent.locks.ReentrantLock

class FutureResult<R> {

    private val locker = ReentrantLock()
    private val condition = locker.newCondition()

    private var isResult = false

    var result: R? = null
        get() {
            locker.lock()
            val result = if (isResult) field
            else {
                condition.await()
                field
            }
            locker.unlock()
            return result
        }
        set(value) {
            locker.lock()
            field = value
            isResult = true
            condition.signalAll()
            locker.unlock()
        }
}