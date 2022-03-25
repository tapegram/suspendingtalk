package suspended

import arrow.fx.coroutines.parZip
import arrow.fx.coroutines.raceN

suspend fun measureTime(block: suspend () -> Unit): Int {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    return ((end - start) / 1.0e9).toInt()
}

typealias Value = Int

typealias Symbol = String

interface StockRepo {
    suspend fun getStock(symbol: Symbol): Value
}

data class InMemoryStockRepo(private val stocks: Map<Symbol, Value>) : StockRepo {
    override suspend fun getStock(symbol: Symbol): Value {
        Thread.sleep(1000)
        return stocks[symbol]!!
    }
}

object GatewayThatFetchesFromManySourcesToFindTheFastestOne : StockRepo {
    override suspend fun getStock(symbol: Symbol): Value =
        raceN(
            { getStockFromSource1(symbol) },
            { getStockFromSource2(symbol) },
        ).fold(
            { throw RuntimeException(it.toString()) },
            { it }
        )

    private suspend fun getStockFromSource1(symbol: Symbol): Value {
        Thread.sleep(900)
        return 1
    }

    private suspend fun getStockFromSource2(symbol: Symbol): Value {
        Thread.sleep(870)
        return 1
    }

}

object GatewayThatGetsTheAverageOfMultipleSources : StockRepo {
    override suspend fun getStock(symbol: Symbol): Value =
        parZip(
            { getStockFromSource1(symbol) },
            { getStockFromSource2(symbol) },
        ) { a, b ->
            // Return the average
            (a + b) / 2
        }

    private suspend fun getStockFromSource1(symbol: Symbol): Value {
        Thread.sleep(900)
        return 10
    }

    private suspend fun getStockFromSource2(symbol: Symbol): Value {
        Thread.sleep(900)
        return 20
    }
}
