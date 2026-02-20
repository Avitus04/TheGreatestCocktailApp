package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.R.attr.category
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.isen.turcheschi.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.turcheschi.thegreatestcocktailapp.DrinksActivity
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.turcheschi.thegreatestcocktailapp.managers.FavoritesManager

@Composable
fun FavoritesScreen(modifier: Modifier, setAppBar: (AppBarState) -> Unit) {

    val context = LocalContext.current

    val manager = remember(context) {
        FavoritesManager(context.applicationContext)
    }

    var favorites by remember {
        mutableStateOf<List<Drink>>(emptyList())
    }

    LaunchedEffect(Unit) {
        favorites = manager.getFavorites()
        setAppBar(
            AppBarState(
                title = "Favorites",
            )
        )
    }

    if (favorites.isEmpty()) {

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorites yet")
        }

    } else {

        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(favorites) { drink ->

                Card (Modifier.clickable {
                    val intent = Intent(context, DetailCocktailActivity::class.java)
                    intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                    context.startActivity(intent)
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            modifier = Modifier.size(80.dp)
                        )

                        Text(
                            text = drink.strDrink ?: "",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}