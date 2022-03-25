package suspended.usecases

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
    val currPrice = stockGateway.getStock(stock)
    val prices = stockHistoryRepo.fetchPreviousStockValues(stock) + currPrice

    /*
    PURE
     */
    val recommendation = prices.analyze()

    /*
    IMPURE
     */
    stockHistoryRepo.appendToHistory(stock, currPrice)

    return recommendation
}

