package com.bvic.followmycrypto.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

class BinanceApi(private val client: HttpClient) {

    suspend fun getTickers(pairs: List<String>): List<TickerDto> {
        // ["BTCUSDT", "ETHUSDT"] devient le texte ["BTCUSDT","ETHUSDT"]
        val symbols = pairs.joinToString(separator = ",", prefix = "[", postfix = "]") { "\"$it\"" }
        return client.get("$BASE_URL/api/v3/ticker/24hr") {
            parameter("symbols", symbols)
        }.body()
    }

    suspend fun getPriceHistory(pair: String): List<Double> {
        val candles: JsonArray = client.get("https://data-api.binance.vision/api/v3/klines") {
            parameter("symbol", pair)      // ex : "BTCUSDT"
            parameter("interval", "1h")
            parameter("limit", 24)
        }.body()

        // Chaque bougie est un tableau : l'index 4 est le prix de clôture, sous forme de texte
        return candles.map { it.jsonArray[4].jsonPrimitive.content.toDouble() }
    }

    private companion object {
        // Domaine de Binance dédié aux données publiques de marché, sans clé d'API
        const val BASE_URL = "https://data-api.binance.vision"
    }

}
