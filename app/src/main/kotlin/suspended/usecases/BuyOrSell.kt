package suspended.usecases

import arrow.fx.coroutines.parZip
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import suspended.core.Recommendation
import suspended.core.Symbol
import suspended.core.analyze

/*
Note the "Purity Sandwich"
 */
suspend fun Service.buyOrSell(stock: Symbol): Recommendation {
    /*
    IMPURE
     */
    val (currPrice, prices) = parZip(
        { stockGateway.getStock(stock) },
        { stockHistoryRepo.fetchPreviousStockValues(stock) }
    ) { current, historical ->
        Pair(current, historical + current)
    }

    /*
    PURE
     */
    val recommendation = prices.analyze()

    /*
    IMPURE
     */
    fireAndForget { stockHistoryRepo.appendToHistory(stock, currPrice) }

    return recommendation
}


//private suspend fun Service.appendToHistory(stock: Symbol, currPrice: Value) = coroutineScope {
//    async { stockHistoryRepo.appendToHistory(stock, currPrice) }
//}


@OptIn(DelicateCoroutinesApi::class)
suspend fun fireAndForget(block: suspend () -> Unit): Job =
    GlobalScope.launch {
        block()
    }

