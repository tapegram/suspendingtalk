suspend fun <A> A.printResult(): A =
    this.also {
        println("Result: $this")
    }

suspend fun Int.printSeconds(): Int =
    this.also {
        println("Time: $it")
    }
