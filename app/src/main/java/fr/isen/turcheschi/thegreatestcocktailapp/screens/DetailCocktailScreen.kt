package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.turcheschi.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.turcheschi.thegreatestcocktailapp.models.Category
import fr.isen.turcheschi.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun RandomCocktailScreen(modifier: Modifier = Modifier, setAppBar: (AppBarState) -> Unit){
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {
        val call = ApiClient.retrofit.getRandomCocktail()
        call.enqueue(object: retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse>,
                response: retrofit2.Response<CocktailResponse>
            ) {
                drink.value = response?.body()?.drinks?.first()
            }

            override fun onFailure(call: Call<CocktailResponse?>?, t: Throwable?) {
                Log.e("request", "getRandom failed ${t?.message}")
            }
        })
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink, setAppBar)
    } ?:run {
        Text("Running")
    }
}


@Composable
fun DetailCocktailScreen(drinkId: String, modifier: Modifier, setAppBar: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
//        drink.value = ApiClient.retrofit.getRandom().drinks?.first()
        val call = ApiClient.retrofit.getDetailCocktail(drinkId)
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })

        setAppBar(
            AppBarState(
                title = "Detail",
                actions = {
                    DetailCocktailTopButton(drink.value)
                }
            )
        )
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink, setAppBar)
    } ?: run {
        Text("Loading")
    }
}

@Composable
fun DetailCocktailScreen(
    modifier: Modifier = Modifier,
    drink: Drink,
    setAppBar: (AppBarState) -> Unit
) {

    val context = LocalContext.current
    setAppBar(
        AppBarState(
            title = "Detail",
            actions = {
                DetailCocktailTopButton(drink)
            }
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier.background(colorResource(R.color.sand_background))
                .fillMaxSize()
        )
        {
            Column(
                modifier = modifier.fillMaxSize().verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    drink.strDrinkThumb,
                    "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(20.dp)
                        .width(200.dp)
                        .height(200.dp)
                        .shadow(18.dp, CircleShape)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.teal_200),
                            CircleShape
                        )
                )

                Text(
                    text = drink.strDrink.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = colorResource(
                        R.color.text_dark
                    ),
                    modifier = Modifier.padding(vertical = 25.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (drink.strAlcoholic.toString() == "Alcoholic")
                    {
                        CategoryView(Category.ALCOHOLIC)
                    }
                    if (drink.strAlcoholic.toString() == "Other")
                    {
                        CategoryView(Category.OTHER)
                    }
                    if (drink.strAlcoholic.toString() == "Non alcoholic") {
                        CategoryView(Category.NON_ALCOHOLIC)
                    }
                }

                Row() {
                    Text(
                        text = drink.strGlass.toString(),
                        color = colorResource(R.color.ocean_blue),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }

                Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp).shadow(8.dp, RoundedCornerShape(16.dp))) {
                    Column(
                        Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        colorResource(R.color.card_tropical),
                                        colorResource(R.color.sunset_orange)
                                    )
                                )
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    , verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Ingredients:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = colorResource(
                                R.color.section_title
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val ingredients = drink.ingredientList().orEmpty()
                        if (ingredients.isEmpty()) {
                            Text("No ingredients", color = colorResource(R.color.text_dark))
                        } else {
                            ingredients.forEach { (ing, measure) ->
                                val line = if (measure.isBlank()) ing else "$measure $ing"
                                Text(line, color = colorResource(R.color.text_dark))
                            }
                        }
                    }
                }

                Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp).shadow(8.dp, RoundedCornerShape(16.dp))) {
                    Column(
                        Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        colorResource(R.color.card_tropical),
                                        colorResource(R.color.sunset_orange)
                                    )
                                )
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Recipe:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = colorResource(
                                R.color.section_title
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            drink.strInstructions?.takeIf { !it.isNullOrBlank() }
                                ?: drink.strInstructions
                                ?: "No instructions",
                            color = colorResource(R.color.text_dark)
                        )

                    }
                }

            }
        }
    }
}
@Composable
fun CategoryView(category: Category)
{
    Box(modifier = Modifier.clip(CircleShape)
        .background(
        Brush.horizontalGradient(
            Category.colors((category))
        )

    ).padding(vertical = 10.dp, horizontal = 20.dp))
    {
        Text(
            text = Category.toString(category),
            fontSize = 18.sp,
            color = colorResource(R.color.text_dark),
        )
    }
}


@Composable
fun DetailCocktailTopButton(drink: Drink?) {

    val context = LocalContext.current

    val manager = remember(context) {
        FavoritesManager(context.applicationContext)
    }

    var isFavorite by remember(drink) {
        mutableStateOf(
            drink?.let { manager.isFavorite(it) } ?: false
        )
    }

    IconButton(
        onClick = {
            drink?.let {
                manager.toggleFavorite(it)
                isFavorite = !isFavorite
            }
        }
    ) {
        Icon(
            imageVector = if (isFavorite)
                Icons.Filled.Favorite
            else
                Icons.Filled.FavoriteBorder,
            contentDescription = null
        )
    }
}