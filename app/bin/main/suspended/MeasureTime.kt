package suspended

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
    override suspend fun getStock(symbol: Symbol): Value {}
}
