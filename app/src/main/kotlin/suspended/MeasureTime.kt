package suspended

suspend fun measureTime(block: suspend () -> Unit): Int {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    return ((end - start) / 1.0e9).toInt()
}

