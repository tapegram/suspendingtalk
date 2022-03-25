package suspended.core

typealias Value = Int
typealias Values = List<Value>
typealias Symbol = String

interface StockGateway {
    suspend fun getStock(symbol: Symbol): Value
}

interface StockHistoryRepo {
    suspend fun fetchPreviousStockValues(symbol: Symbol): Values
    suspend fun appendToHistory(symbol: Symbol, value: Value): Unit
}
