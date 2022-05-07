import java.text.SimpleDateFormat
import java.util.Date

fun main() {
    val exec = CustomExecutor<Int, Int>(maxNumThreads = 2)

    val futures = exec.map(::longRunningTask, listOf(1, 2, 3, 4))
    futures.forEach {
        println("${it.result} - Time: ${getCurrentTime()}")
    }
}

fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm:ss")
    val date = Date(System.currentTimeMillis())
    return formatter.format(date)
}

fun longRunningTask(x: Int): Int {
    Thread.sleep(2000L) // wait for 2 seconds
    return x * 2
}