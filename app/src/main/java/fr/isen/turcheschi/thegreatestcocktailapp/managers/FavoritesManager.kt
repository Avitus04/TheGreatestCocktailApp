package fr.isen.turcheschi.thegreatestcocktailapp.managers

import android.content.Context
import androidx.compose.ui.util.fastCbrt
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.Drink

data class Favorites(
    @SerializedName("favorites")
    var favorites: MutableList<Drink> = mutableListOf()
)

class FavoritesManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    private val key = "favorites_list"

    private fun loadFavorites(): MutableList<Drink> {
        val json = prefs.getString(key, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Drink>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveFavorites(favorites: MutableList<Drink>) {
        val json = gson.toJson(favorites)
        prefs.edit().putString(key, json).apply()
    }

    fun toggleFavorite(drink: Drink) {
        val favorites = loadFavorites()

        val exists = favorites.any { it.idDrink == drink.idDrink }

        if (exists) {
            favorites.removeAll { it.idDrink == drink.idDrink }
        } else {
            favorites.add(drink)
        }

        saveFavorites(favorites)
    }

    fun isFavorite(drink: Drink): Boolean {
        val favorites = loadFavorites()
        return favorites.any { it.idDrink == drink.idDrink }
    }

    fun getFavorites(): List<Drink> {
        return loadFavorites()
    }
}