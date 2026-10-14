package com.bvic.followmycrypto.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun CryptoListScreen(
    onCryptoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CryptoListViewModel = viewModel { CryptoListViewModel(AppContainer.cryptoRepository,
        AppContainer.favoritesRepository) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showFavoritesOnly by viewModel.showFavoritesOnly.collectAsStateWithLifecycle()

    CryptoListContent(
        uiState = uiState,
        onRefresh = viewModel::refresh,
        onCryptoClick = onCryptoClick,
        onFavoriteClick = viewModel::toggleFavorite,
        showFavoritesOnly = showFavoritesOnly,
        onToggleFavoritesFilter = viewModel::toggleFavoritesFilter,
        modifier = modifier,
    )
}

// Version sans état : elle ne connaît pas le ViewModel, on peut donc l'afficher dans un aperçu
@Composable
fun CryptoListContent(
    uiState: CryptoListUiState,
    onRefresh: () -> Unit,
    onCryptoClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    showFavoritesOnly: Boolean,
    onToggleFavoritesFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FollowMyCrypto", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Rafraîchir")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextButton(
                onClick = onToggleFavoritesFilter,
                modifier = Modifier.padding(start = 12.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Gray
                )
            ) {
                Text(
                    text = if (showFavoritesOnly) "Toutes" else "Favoris seulement"
                )
            }

            when (uiState) {
                CryptoListUiState.Loading -> LoadingContent(
                    modifier = Modifier.weight(1f)
                )

                is CryptoListUiState.Error -> ErrorContent(
                    message = uiState.message,
                    onRetry = onRefresh,
                    modifier = Modifier.weight(1f)
                )

                is CryptoListUiState.Success -> CryptoList(
                    cryptos = uiState.cryptos,
                    favoriteSymbols = uiState.favoriteSymbols,
                    onCryptoClick = onCryptoClick,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    }

@Composable
fun CryptoList(
    cryptos: List<Crypto>,
    favoriteSymbols: Set<String>,
    onCryptoClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = cryptos, key = { it.symbol }) { crypto ->
            CryptoRow(
                crypto = crypto,
                isFavorite= crypto.symbol in favoriteSymbols,
                onCryptoClick = { onCryptoClick(crypto.symbol) },
                onFavoriteClick = { onFavoriteClick(crypto.symbol) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun CryptoRow(
        crypto: Crypto,
        isFavorite: Boolean,
        onCryptoClick: () -> Unit,
        onFavoriteClick: () -> Unit,
        modifier: Modifier = Modifier)
    {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCryptoClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = crypto.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = crypto.symbol,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatPrice(crypto.price),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = formatPercent(crypto.changePercent),
                style = MaterialTheme.typography.bodySmall,
                color = if (crypto.changePercent >= 0) PriceUp else PriceDown,
            )
        }
        IconButton(onClick = onFavoriteClick){
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Selected in favorites" else "Unselected in favorites"
            )
        }
    }
}

private val previewCryptos = listOf(
    Crypto(symbol = "BTC", name = "Bitcoin", price = 85613.50, changePercent = 0.88, highPrice = 86000.0, lowPrice = 84000.0,),
    Crypto(symbol = "ETH", name = "Ethereum", price = 3214.07, changePercent = -1.42,highPrice = 3300.0,lowPrice = 3200.0,),
)

@Preview(showBackground = true)
@Composable
fun CryptoRowPreview() {
    FollowMyCryptoTheme {
        CryptoRow(crypto = previewCryptos.first(), isFavorite = false, onCryptoClick = {}, onFavoriteClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoListContentPreview() {
    FollowMyCryptoTheme {
        CryptoListContent(uiState = CryptoListUiState.Success(previewCryptos, emptySet()), onRefresh = {}, onCryptoClick = {}, onFavoriteClick = {}, showFavoritesOnly = false,
            onToggleFavoritesFilter = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoListErrorPreview() {
    FollowMyCryptoTheme {
        CryptoListContent(uiState = CryptoListUiState.Error("Impossible de charger les cours."), onRefresh = {}, onCryptoClick = {}, onFavoriteClick = {}, showFavoritesOnly = false,
            onToggleFavoritesFilter = {})
    }
}
