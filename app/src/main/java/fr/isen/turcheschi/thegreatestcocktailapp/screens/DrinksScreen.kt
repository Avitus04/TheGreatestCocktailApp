package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.isen.turcheschi.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.turcheschi.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response
import  retrofit2.Callback

@Composable
fun DrinksScreen(
    modifier: Modifier,
    categoryID: String?
) {

    var drinks by remember { mutableStateOf<List<DrinkPreview>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(categoryID) {

        if (categoryID != null) {
            ApiClient.retrofit.getDrinksPreview(categoryID)
                .enqueue(object : Callback<DrinkFilterResponse> {

                    override fun onResponse(
                        call: Call<DrinkFilterResponse>,
                        response: Response<DrinkFilterResponse>
                    ) {
                        drinks = response.body()?.drinks ?: emptyList()
                        Log.d("API_DEBUG", "Drinks: $drinks")
                    }

                    override fun onFailure(
                        call: Call<DrinkFilterResponse>,
                        t: Throwable
                    ) {
                        Log.e("request", "getDrinksPreview failed ${t.message}")
                    }
                })
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.sand_background))
    )
    {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            items(drinks) { drink ->

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
                            .padding(horizontal =  16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = colorResource(R.color.ocean_blue),
                                    CircleShape
                                )
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