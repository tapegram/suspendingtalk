package suspended.core

sealed class Recommendation {
    object BUY : Recommendation()
    object SELL : Recommendation()
    object HOLD : Recommendation()
}

/*
Our secret sauce, "The Algorithm"
 */
fun Values.analyze(): Recommendation {
    if (!this.hasEnoughInfo()) {
        // Not enough info!
        return Recommendation.HOLD
    }

    if (this.hasPriceDroppedSinceLastTime()) {
        // Panic Sell!
        return Recommendation.SELL
    }

    // To the moon, baby!
    return Recommendation.BUY
}

fun Values.hasEnoughInfo(): Boolean =
    this.size >= 2

fun Values.hasPriceDroppedSinceLastTime(): Boolean =
    this.last() < this.previous()

fun Values.previous(): Value =
    this.takeLast(2).first()
