package com.bvic.followmycrypto.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvic.followmycrypto.data.CryptoRepository
import com.bvic.followmycrypto.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// L'état du graphique, indépendant de celui de l'écran
sealed interface ChartUiState {
    data object Loading : ChartUiState
    data class Success(val prices: List<Double>) : ChartUiState
    data object Error : ChartUiState
}

class CryptoDetailViewModel(
    private val symbol: String,
    private val repository: CryptoRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CryptoDetailUiState>(CryptoDetailUiState.Loading)
    val uiState: StateFlow<CryptoDetailUiState> = _uiState.asStateFlow()

    val isFavorite = favoritesRepository.favorites
        .map { favorites -> symbol in favorites }
    private val _chartState = MutableStateFlow<ChartUiState>(ChartUiState.Loading)
    val chartState: StateFlow<ChartUiState> = _chartState.asStateFlow()

    init {
        load()
        loadChart()
    }

    fun retry() {
        load()
        loadChart()
    }

    private fun load() {
        _uiState.value = CryptoDetailUiState.Loading
        viewModelScope.launch {
            try {
                _uiState.value = CryptoDetailUiState.Success(repository.getCrypto(symbol))
            } catch (e: Exception) {
                _uiState.value = CryptoDetailUiState.Error("Impossible de charger $symbol.")
            }
        }
    }

    fun toggleFavorite() {
        favoritesRepository.toggleFavorite(symbol)
    }

    private fun loadChart() {
        _chartState.value = ChartUiState.Loading
        viewModelScope.launch {
            try {
                _chartState.value = ChartUiState.Success(repository.getPriceHistory(symbol))
            } catch (e: Exception) {
                // Une erreur du graphique n'affecte pas le reste de l'écran
                _chartState.value = ChartUiState.Error
            }
        }
    }
}
