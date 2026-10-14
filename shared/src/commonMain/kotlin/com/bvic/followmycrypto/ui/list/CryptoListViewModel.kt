package com.bvic.followmycrypto.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bvic.followmycrypto.data.CryptoRepository
import com.bvic.followmycrypto.data.FavoritesRepository
import com.bvic.followmycrypto.model.Crypto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val repository: CryptoRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    // Version modifiable, privée : seul le ViewModel peut changer l'état
    private val _uiState = MutableStateFlow<CryptoListUiState>(CryptoListUiState.Loading)
    private val _cryptos = MutableStateFlow<List<Crypto>>(emptyList())
    private val _showFavoritesOnly = MutableStateFlow(false)

    // Version en lecture seule, exposée à l'interface
    val uiState: StateFlow<CryptoListUiState> = _uiState.asStateFlow()
    val showFavoritesOnly: StateFlow<Boolean> = _showFavoritesOnly.asStateFlow()

    init {
        load()
        viewModelScope.launch {
            combine(_cryptos, favoritesRepository.favorites, _showFavoritesOnly) { cryptos, favoriteSymbols, favoriteOnly ->

                val filteredCryptos =
                    if (favoriteOnly){
                        cryptos.filter { crypto ->
                            crypto.symbol in favoriteSymbols
                        }
                    } else {
                        cryptos
                    }

                CryptoListUiState.Success(filteredCryptos, favoriteSymbols)

            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun refresh() = load()

    private fun load() {
        _uiState.value = CryptoListUiState.Loading
        viewModelScope.launch {
            try {
                _cryptos.value = repository.getCryptos()
            } catch (e: Exception) {
                _uiState.value = CryptoListUiState.Error("Impossible de charger les cours.")
            }
        }
    }

    fun toggleFavorite(symbol: String) {
        favoritesRepository.toggleFavorite(symbol)
    }

    fun toggleFavoritesFilter() {
        _showFavoritesOnly.value = !_showFavoritesOnly.value
    }
}
