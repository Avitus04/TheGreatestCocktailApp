package fr.isen.turcheschi.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.turcheschi.thegreatestcocktailapp.screens.AppBarState
import fr.isen.turcheschi.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.turcheschi.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.turcheschi.thegreatestcocktailapp.screens.RandomCocktailScreen
import fr.isen.turcheschi.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DetailCocktailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val drinkId = intent.getStringExtra(DRINKID) ?: ""
        setContent {
            var appBarState by remember { mutableStateOf(AppBarState(title = "Default")) }
            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = appBarState.title) },
                            actions = {
                                appBarState.actions?.invoke(this)
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(R.color.sunset_orange),
                                titleContentColor = colorResource(R.color.text_dark),
                                actionIconContentColor = colorResource(R.color.grey_200)
                            )
                        )
                    },
                ) { innerPadding ->
                    DetailCocktailScreen(drinkId, Modifier.padding(innerPadding),
                        setAppBar = { state -> appBarState = state })
                }
            }
        }
    }

    companion object{
        const val DRINKID = "drinkid"
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    TheGreatestCocktailAppTheme {
        Greeting2("Android")
    }
}