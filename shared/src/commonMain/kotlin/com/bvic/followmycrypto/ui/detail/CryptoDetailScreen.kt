package com.bvic.followmycrypto.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
<<<<<<< HEAD
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
=======
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bvic.followmycrypto.data.AppContainer
import com.bvic.followmycrypto.model.Crypto
import com.bvic.followmycrypto.theme.FollowMyCryptoTheme
import com.bvic.followmycrypto.theme.PriceDown
import com.bvic.followmycrypto.theme.PriceUp
import com.bvic.followmycrypto.ui.components.ErrorContent
import com.bvic.followmycrypto.ui.components.LoadingContent
import com.bvic.followmycrypto.ui.format.formatPercent
import com.bvic.followmycrypto.ui.format.formatPrice

@Composable
fun CryptoDetailScreen(
    symbol: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CryptoDetailViewModel = viewModel { CryptoDetailViewModel(symbol, AppContainer.cryptoRepository,
        AppContainer.favoritesRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
<<<<<<< HEAD
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle(
        initialValue = false
    )
=======
    val chartState by viewModel.chartState.collectAsStateWithLifecycle()
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)

    CryptoDetailContent(
        symbol = symbol,
        uiState = uiState,
<<<<<<< HEAD
        isFavorite = isFavorite,
        onFavoriteClick = viewModel::toggleFavorite,
=======
        chartState = chartState,
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)
        onBack = onBack,
        onRetry = viewModel::retry,
        modifier = modifier,
    )
}

@Composable
fun CryptoDetailContent(
    symbol: String,
    uiState: CryptoDetailUiState,
<<<<<<< HEAD
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
=======
    chartState: ChartUiState,
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(symbol, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Filled.FavoriteBorder
                            },
                            contentDescription = if (isFavorite) {
                                "Retirer des favoris"
                            } else {
                                "Ajouter aux favoris"
                            }
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding).fillMaxSize()
        when (uiState) {
            CryptoDetailUiState.Loading -> LoadingContent(modifier = contentModifier)
            is CryptoDetailUiState.Error -> ErrorContent(
                message = uiState.message,
                onRetry = onRetry,
                modifier = contentModifier,
            )
            is CryptoDetailUiState.Success -> CryptoDetail(
                crypto = uiState.crypto,
                chartState = chartState,
                modifier = contentModifier,
            )
        }
    }
}

@Composable
fun CryptoDetail(
    crypto: Crypto,
    chartState: ChartUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = crypto.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = formatPrice(crypto.price),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "${formatPercent(crypto.changePercent)} sur 24h",
            style = MaterialTheme.typography.titleMedium,
            color = if (crypto.changePercent >= 0) PriceUp else PriceDown,
        )

<<<<<<< HEAD
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Plus haut 24h",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = formatPrice(crypto.highPrice),
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Plus bas 24h",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = formatPrice(crypto.lowPrice),
                fontWeight = FontWeight.Bold,
=======
        Spacer(modifier = Modifier.height(16.dp))

        when (chartState) {
            ChartUiState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
            )
            is ChartUiState.Success -> PriceChart(prices = chartState.prices)
            ChartUiState.Error -> Text(
                text = "Graphique indisponible",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoDetailContentPreview() {
    FollowMyCryptoTheme {
        CryptoDetailContent(
            symbol = "BTC",
            uiState = CryptoDetailUiState.Success(
                Crypto(symbol = "BTC", name = "Bitcoin", price = 85613.50, changePercent = 0.88, highPrice = 86000.0,lowPrice = 84000.0,),
            ),
<<<<<<< HEAD
            isFavorite = false,
            onFavoriteClick = {},
=======
            chartState = ChartUiState.Success(listOf(100.0, 104.0, 101.0, 108.0, 106.0, 112.0)),
>>>>>>> fd2bee6 (Commit 6 and 17 evo +)
            onBack = {},
            onRetry = {},
        )
    }
}