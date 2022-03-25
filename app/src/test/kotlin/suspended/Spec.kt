package suspended

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

val inMemoryStockRepo =
    InMemoryStockRepo(
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
