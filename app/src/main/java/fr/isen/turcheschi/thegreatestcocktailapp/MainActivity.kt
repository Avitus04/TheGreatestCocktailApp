package fr.isen.turcheschi.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.turcheschi.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.turcheschi.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            val randomItem = TabBarItem("Random", Icons.Filled.Refresh,Icons.Outlined.Refresh)
            val categoryItem = TabBarItem("Category", Icons.Filled.Menu, Icons.Outlined.Menu)
            val favoritesItem = TabBarItem("Favorites", Icons.Filled.Favorite,Icons.Outlined.Favorite)
            val tabItems = listOf(randomItem, categoryItem, favoritesItem)

            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Detail")
                        }, actions = {
                            IconButton(onClick = {
                                Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT)
                                    .show()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = "Localized description"
                                )
                            }
                        })
                    },
                    bottomBar = {BottomAppBar(tabItems, navController)}
                ) { innerPadding ->
                    NavHost(navController, randomItem.title){
                        composable(randomItem.title) {
                            DetailCocktailScreen(
                                Modifier.padding(innerPadding)
                            )
                        }
                        composable(categoryItem.title) {
                            CategoriesScreen(
                                Modifier.padding(innerPadding)
                            )
                        }
                        composable(favoritesItem.title) {
                            FavoritesScreen(
                                Modifier.padding(innerPadding)
                            )
                        }
                    }
//                    DetailCocktailScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheGreatestCocktailAppTheme {
        DetailCocktailScreen()
    }
}