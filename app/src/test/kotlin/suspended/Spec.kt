package suspended

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import suspended.adapters.GatewayThatFetchesFromManySourcesToFindTheFastestOne
import suspended.adapters.GatewayThatGetsTheAverageOfMultipleSources
import suspended.adapters.InMemoryStockGateway

val inMemoryStockRepo =
    InMemoryStockGateway(
        mapOf(
            "PTON" to 100,
        )
    )

class StockSpec :
    StringSpec({
        "test in memory lookup" {
            measureTime {
                inMemoryStockRepo.getStock("PTON") shouldBe 100
            } shouldBe 1
        }

        "test with a race" {
            measureTime {
                GatewayThatFetchesFromManySourcesToFindTheFastestOne
                    .getStock("PTON") shouldBe 1
            } shouldBe 0
        }

        "test with a par zip" {
            measureTime {
                GatewayThatGetsTheAverageOfMultipleSources
                    .getStock("PTON") shouldBe 15
            } shouldBe 0
        }
    })
