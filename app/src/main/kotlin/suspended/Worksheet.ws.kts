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

Examples:
Performance
[] Easily parallelize independent effects
[] "Fire and Forget" when appropriate
[] Further optimize lookups in the StockGateway

Architecture
[] Compiler reminds you not to use effects in your pure domain
 */