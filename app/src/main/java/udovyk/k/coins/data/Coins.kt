package udovyk.k.coins.data

import udovyk.k.coins.R

enum class Coins(val value: Int, val image: Int) {
    GOLD(2, R.drawable.ic_coin_gold),
    SILVER(1, R.drawable.ic_coin_silver),
    RED(-3, R.drawable.ic_coin_red)
}