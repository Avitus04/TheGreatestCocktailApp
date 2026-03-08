package fr.isen.turcheschi.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.turcheschi.thegreatestcocktailapp.screens.AppBarState
import fr.isen.turcheschi.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.turcheschi.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.RandomCocktailScreen
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

            var appBarState by remember { mutableStateOf(AppBarState(title = "Default")) }
            TheGreatestCocktailAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    colorResource(R.color.purple_700),
                                    colorResource(R.color.purple_900)
                                )
                            )
                        ),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = appBarState.title) },
                            actions = {
                                appBarState.actions?.invoke(this)
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(R.color.sunset_orange),
                                titleContentColor = colorResource(R.color.text_dark),
                                actionIconContentColor = colorResource(R.color.text_dark)
                            ),
                            modifier = Modifier.drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                drawLine(
                                    color = Color(0x33FFFFFF),
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                            }
                        )
                    },
                    bottomBar = { BottomAppBar(tabItems, navController) }
                ) { innerPadding ->
                    NavHost(navController, randomItem.title){
                        composable(randomItem.title) {
                            RandomCocktailScreen(
                                Modifier.padding(innerPadding),
                                setAppBar = { state -> appBarState = state }
                            )
                        }
                        composable(categoryItem.title) {
                            CategoriesScreen(
                                Modifier.padding(innerPadding),
                                setAppBar = { state -> appBarState = state }
                            )
                        }
                        composable(favoritesItem.title) {
                            FavoritesScreen(
                                Modifier.padding(innerPadding),
                                setAppBar = { state -> appBarState = state }
                            )
                        }
                    }
                }
            }
        }
    }
}
