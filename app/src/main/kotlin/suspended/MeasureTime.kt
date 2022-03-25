package suspended

import kotlinx.coroutines.runBlocking

suspend fun measureTime(block: suspend () -> Unit): Int {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    return ((end - start) / 1.0e9).toInt()
}

suspend fun <A> A.printResult(): A =
    this.also {
        println("Result: $this")
    }

suspend fun Int.printSeconds(): Int =
    this.also {
        println("Time: $it")
    }

fun blockAndTime(block: suspend () -> Unit): Int =
    runBlocking { measureTime { block() }.printSeconds() }
