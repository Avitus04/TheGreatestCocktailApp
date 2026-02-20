package fr.isen.turcheschi.thegreatestcocktailapp.network

import android.util.Log
import androidx.lifecycle.ViewModel
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CocktailViewModel : ViewModel() {

    private val _cocktail = MutableStateFlow<Drink?>(null)
    val cocktail: StateFlow<Drink?> = _cocktail

    fun loadRandomCocktail() {

        NetworkManager.getRandomCocktail(

            onSuccess = { response ->
                Log.d("API_DEBUG", "Full response: $response")
                _cocktail.value = response.drinks?.firstOrNull()
            },

            onError = { error ->
                error.printStackTrace()
            }
        )
    }
}

