package suspended.usecases

import suspended.core.StockGateway
import suspended.core.StockHistoryRepo

data class Service(
    val stockGateway: StockGateway,
    val stockHistoryRepo: StockHistoryRepo,
)