package com.bvic.followmycrypto.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoritesRepository {
    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    fun toggleFavorite(symbol: String){
        if (symbol in favorites.value){
            _favorites.update { currentState ->
                currentState - symbol
            }
        }else{
            _favorites.update { currentState ->
                currentState + symbol
            }
        }
    }
}