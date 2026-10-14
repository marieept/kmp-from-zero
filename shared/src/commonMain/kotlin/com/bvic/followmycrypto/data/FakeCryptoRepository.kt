package com.bvic.followmycrypto.data

import com.bvic.followmycrypto.model.Crypto
import kotlinx.coroutines.delay

private val mockCryptos = listOf(
    Crypto(symbol = "BTC", name = "Bitcoin", price = 85613.50, changePercent = 0.88, highPrice = 86000.0, lowPrice = 84000.0),
    Crypto(symbol = "ETH", name = "Ethereum", price = 3214.07, changePercent = -1.42, highPrice = 3300.0, lowPrice = 3200.0),
    Crypto(symbol = "BNB", name = "BNB", price = 642.30, changePercent = 0.15, highPrice = 700.0, lowPrice = 600.0),
    Crypto(symbol = "SOL", name = "Solana", price = 148.92, changePercent = 4.31,highPrice = 200.0, lowPrice = 100.0),
    Crypto(symbol = "XRP", name = "XRP", price = 0.5873, changePercent = -0.64, highPrice = 0.6, lowPrice = 0.5),
    Crypto(symbol = "ADA", name = "Cardano", price = 0.4521, changePercent = 2.07, highPrice = 0.5, lowPrice = 0.4),
    Crypto(symbol = "DOGE", name = "Dogecoin", price = 0.1284, changePercent = -3.95, highPrice = 0.2, lowPrice = 0.1),
    Crypto(symbol = "SHIB", name = "Shiba Inu", price = 0.00001834, changePercent = 1.12, highPrice = 0.00002, lowPrice = 0.00001),
)

// Un faux repository : il simule un serveur lent, sans réseau
class FakeCryptoRepository : CryptoRepository {

    override suspend fun getCryptos(): List<Crypto> {
        delay(1_500)
        return mockCryptos
    }

    override suspend fun getCrypto(symbol: String): Crypto {
        delay(500)
        return mockCryptos.first { it.symbol == symbol }
    }
}
