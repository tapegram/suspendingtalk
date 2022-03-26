import suspended.adapters.MockStockGateway
import suspended.adapters.MockStockHistoryRepo
import suspended.blockAndTime
import suspended.usecases.Service
import suspended.usecases.buyOrSell

blockAndTime {
    Service(
        stockGateway = MockStockGateway(
            _getStock = {
                wait(3000)
                5
            }
        ),
        stockHistoryRepo = MockStockHistoryRepo(
            _fetchPreviousStockValues = { _ ->
                wait(3000)
                listOf(1, 2, 3)
            },
            _appendToHistory = { _, _ ->
                wait(3000)
                Unit
            }
        )
    ).buyOrSell("PTON")
}
suspend fun wait(x: Int): Unit = Thread.sleep(x.toLong())

/*
Pitch:
Default to making your "ports" `suspend`

```
interface StockGateway {
    suspend fun getStock(symbol: Symbol): Value
}

interface StockHistoryRepo {
    suspend fun fetchPreviousStockValues(symbol: Symbol): Values
    suspend fun appendToHistory(symbol: Symbol, value: Value): Unit
}
```

Why:
1) This gives you the opportunity to take advantages of async performance improvements as needed when it comes up
2) Ask the compiler to gently encourage following the ports and adapters arch

Some references:
Kotlin Documentation - https://kotlinlang.org/docs/coroutines-guide.html
Arrow Fx - https://arrow-kt.io/docs/fx/
Pure and Referentially Transparent Functions - https://arrow-kt.io/docs/fx/purity-and-referentially-transparent-functions/
Deep Dive into Coroutines - https://www.youtube.com/watch?v=YrrUCSi72E8
Exploring Coroutines - https://www.youtube.com/watch?v=jT2gHPQ4Z1Q&ab_channel=JetBrainsTV
Async Data Streams with Kotlin Flow - https://www.youtube.com/watch?v=tYcqn48SMT8&ab_channel=JetBrainsTV

Examples:
Prep
✅ Have the checkbox emoji ready
✅ Review the slightly opinionated arch and usecases
✅ Review how we are measuring time
✅ What is a coroutine? ->
    - It's a super lightweight thread
    - Coroutines are a general control structure whereby flow control is cooperatively passed between two different routines without returning.
    - Think 'yield' in Python
✅ What is `suspend` ->
    - Kotlin will automatically be able to "suspend" processing the computation at points injected into the function
    - See the Deep Dive into Coroutines talk to get a sense of the implementation
    - Also acts as an "async primitive."
    - Kotlin cares about "structured concurrency" and ergonomics / maintainability of code.

Performance
✅ Easily parallelize independent effects
✅ Further optimize lookups in the StockGateway

Architecture
✅ Compiler reminds you not to use effects in your pure domain
 */


//
//runBlocking {
//    measureTime {
//        wait(1000)
//        wait(1000)
//    }.printSeconds()
//
//    measureTime {
//        parZip(
//            { wait(1000) },
//            { wait(1000) },
//        ) { a, b -> Unit }
//    }.printSeconds()
//}