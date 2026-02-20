package fr.isen.turcheschi.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.turcheschi.thegreatestcocktailapp.DrinksActivity
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.turcheschi.thegreatestcocktailapp.dataClasses.DrinkCategory
import fr.isen.turcheschi.thegreatestcocktailapp.models.Category
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

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->

            Card(
                Modifier.clickable {
                    val intent = Intent(context, DrinksActivity::class.java)
                    intent.putExtra("category", category.strCategory)
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = category.strCategory ?: "",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}