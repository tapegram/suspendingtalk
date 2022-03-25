package suspended.adapters

import suspended.core.StockHistoryRepo
import suspended.core.Symbol
import suspended.core.Value
import suspended.core.Values

data class MockStockHistoryRepo(
    private val _fetchPreviousStockValues: (Symbol) -> Values = { symbol ->
        listOf(1, 2, 3)
    },
    private val _appendToHistory: (Symbol, Value) -> Unit = { _, _ -> Unit },
) : StockHistoryRepo {
    override suspend fun fetchPreviousStockValues(symbol: Symbol): Values =
        _fetchPreviousStockValues(symbol)

    override suspend fun appendToHistory(symbol: Symbol, value: Value) =
        _appendToHistory(symbol, value)
}