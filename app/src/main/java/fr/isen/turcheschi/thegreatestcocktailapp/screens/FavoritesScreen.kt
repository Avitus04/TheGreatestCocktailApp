package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import fr.isen.turcheschi.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.turcheschi.thegreatestcocktailapp.R
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
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                favorites = manager.getFavorites()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (favorites.isEmpty()) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorites yet 🍸",
                color = colorResource(R.color.sunset_orange),
                fontSize = 18.sp
            )
        }

    } else {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.sand_background))
        )
        {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {

                items(favorites) { drink ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val intent = Intent(context, DetailCocktailActivity::class.java)
                                intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                                context.startActivity(intent)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.sunset_orange).copy(alpha = 0.65f)
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            AsyncImage(
                                model = drink.strDrinkThumb,
                                contentDescription = drink.strDrink,
                                modifier = Modifier.size(80.dp)
                            )

                            Text(
                                text = drink.strDrink ?: "",
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = colorResource(R.color.text_dark),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}