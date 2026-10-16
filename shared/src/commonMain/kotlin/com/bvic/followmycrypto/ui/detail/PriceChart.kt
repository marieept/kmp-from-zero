package com.bvic.followmycrypto.ui.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bvic.followmycrypto.theme.FollowMyCryptoTheme
import com.bvic.followmycrypto.theme.PriceDown
import com.bvic.followmycrypto.theme.PriceUp

// Trace la courbe d'une suite de prix, du plus ancien au plus récent
@Composable
fun PriceChart(prices: List<Double>, modifier: Modifier = Modifier) {
    if (prices.size < 2) return

    val color = if (prices.last() >= prices.first()) PriceUp else PriceDown
    val min = prices.min()
    val max = prices.max()
    // Évite une division par zéro si tous les prix sont identiques
    val range = if (max > min) max - min else 1.0

    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)) {
        val stepX = size.width / (prices.size - 1)
        val path = Path()
        prices.forEachIndexed { index, price ->
            val x = index * stepX
            // Le prix le plus haut est en haut : dans un Canvas, y = 0 est le bord supérieur
            val y = size.height - ((price - min) / range).toFloat() * size.height
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriceChartPreview() {
    FollowMyCryptoTheme {
        PriceChart(prices = listOf(100.0, 104.0, 101.0, 108.0, 106.0, 112.0))
    }
}