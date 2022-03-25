import kotlinx.coroutines.runBlocking
import suspended.GatewayThatFetchesFromManySourcesToFindTheFastestOne
import suspended.GatewayThatGetsTheAverageOfMultipleSources
import suspended.InMemoryStockRepo
import suspended.measureTime

val inMemoryStockRepo =
    InMemoryStockRepo(
        mapOf(
            "PTON" to 100,
        )
    )

runBlocking {
    measureTime {
        inMemoryStockRepo.getStock("PTON").also {
            println("Result: $it")
        }
    }.also { println("Time: $it") }
}

//"test with a race" {
//    measureTime {
//        GatewayThatFetchesFromManySourcesToFindTheFastestOne
//            .getStock("PTON") shouldBe 1
//    } shouldBe 0
//}
//
//"test with a par zip" {
//    measureTime {
//        GatewayThatGetsTheAverageOfMultipleSources
//            .getStock("PTON") shouldBe 15
//    } shouldBe 0
//}
