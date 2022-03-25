package suspended

import io.kotest.assertions.fail
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
      "test in memory lookup" { measureTime { inMemoryStockRepo.getStock("PTON") } `shouldBe` 1 }
      "fail" { fail("oop") }
    })
