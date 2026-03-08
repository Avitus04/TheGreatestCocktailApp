package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.turcheschi.thegreatestcocktailapp.DrinksActivity
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkCategory
import fr.isen.turcheschi.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response
import kotlin.jvm.java
import retrofit2.Callback

@Composable
fun CategoriesScreen(modifier: Modifier, setAppBar: (AppBarState) -> Unit) {

    val context = LocalContext.current
    var categories by remember { mutableStateOf<List<DrinkCategory>>(emptyList()) }

    LaunchedEffect(Unit) {
        ApiClient.retrofit.getCategories()
            .enqueue(object : Callback<CategoryListResponse> {
                override fun onResponse(
                    call: Call<CategoryListResponse>,
                    response: Response<CategoryListResponse>
                ) {
                    categories = response.body()?.drinks ?: emptyList()
                    Log.d("API_DEBUG", "Categories: $categories")
                }

                override fun onFailure(
                    call: Call<CategoryListResponse>,
                    t: Throwable
                ) {
                    Log.e("request", "getCategories failed ${t.message}")
                }
            })

        setAppBar(
            AppBarState(
                title = "Categories",
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.sand_background))
    )
    {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            items(categories) { category ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            val intent = Intent(context, DrinksActivity::class.java)
                            intent.putExtra("category", category.strCategory)
                            context.startActivity(intent)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.sunset_orange).copy(alpha = 0.65f)
                    ),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = category.strCategory ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        color = colorResource(R.color.text_dark),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}