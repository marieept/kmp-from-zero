package com.bvic.followmycrypto

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bvic.followmycrypto.theme.FollowMyCryptoTheme
import com.bvic.followmycrypto.ui.detail.CryptoDetailScreen
import com.bvic.followmycrypto.ui.list.CryptoListScreen
import kotlinx.serialization.Serializable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.contentColorFor


// Chaque écran a une « route » : un objet qui l'identifie et porte ses arguments
@Serializable
object ListRoute

@Serializable
data class DetailRoute(val symbol: String)

@Composable
fun App() {
    FollowMyCryptoTheme {
        androidx.compose.material3.Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.outline
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = ListRoute) {
                composable<ListRoute> {
                    CryptoListScreen(
                        onCryptoClick = { symbol -> navController.navigate(DetailRoute(symbol)) },

                        )
                }
                composable<DetailRoute> { backStackEntry ->
                    val route = backStackEntry.toRoute<DetailRoute>()
                    CryptoDetailScreen(
                        symbol = route.symbol,
                        onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
