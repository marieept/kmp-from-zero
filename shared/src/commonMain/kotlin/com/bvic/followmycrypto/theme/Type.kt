
package com.bvic.followmycrypto.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import followmycrypto.shared.generated.resources.Res
import followmycrypto.shared.generated.resources.do_hyeon_regular

@Composable
fun AppTypography(): Typography {
    val doHyeon = FontFamily(
        Font(
            resource = Res.font.do_hyeon_regular,
            weight = FontWeight.Normal
        )
    )

    val baseline = Typography()

    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = doHyeon),
        displayMedium = baseline.displayMedium.copy(fontFamily = doHyeon),
        displaySmall = baseline.displaySmall.copy(fontFamily = doHyeon),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = doHyeon),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = doHyeon),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = doHyeon),
        titleLarge = baseline.titleLarge.copy(fontFamily = doHyeon),
        titleMedium = baseline.titleMedium.copy(fontFamily = doHyeon),
        titleSmall = baseline.titleSmall.copy(fontFamily = doHyeon),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = doHyeon),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = doHyeon),
        bodySmall = baseline.bodySmall.copy(fontFamily = doHyeon),
        labelLarge = baseline.labelLarge.copy(fontFamily = doHyeon),
        labelMedium = baseline.labelMedium.copy(fontFamily = doHyeon),
        labelSmall = baseline.labelSmall.copy(fontFamily = doHyeon)
    )
}
