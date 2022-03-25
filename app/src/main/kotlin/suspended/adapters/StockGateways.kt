package suspended.adapters

import arrow.fx.coroutines.parZip
import arrow.fx.coroutines.raceN
import suspended.core.StockGateway
import suspended.core.Symbol
import suspended.core.Value


data class InMemoryStockGateway(private val stocks: Map<Symbol, Value>) : StockGateway {
    override suspend fun getStock(symbol: Symbol): Value {
        Thread.sleep(1000)
        return stocks[symbol]!!
    }
}

object GatewayThatFetchesFromManySourcesToFindTheFastestOne : StockGateway {
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

object GatewayThatGetsTheAverageOfMultipleSources : StockGateway {
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
