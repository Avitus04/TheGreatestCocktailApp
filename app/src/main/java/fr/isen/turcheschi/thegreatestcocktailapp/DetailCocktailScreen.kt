package fr.isen.turcheschi.thegreatestcocktailapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu


@Composable
fun DetailCocktailScreen(modifier: Modifier = Modifier)
{
    Column(modifier = modifier) {
        MediumTopAppBarExample(modifier)
        Image(
            painter = painterResource(id = R.drawable.cocktail_intro),
            contentDescription = "An image of a lemon cocktail"
        )

        Text(
            text = "Mojito",

        )

        Text(
            text = "Classic Cocktail",

        )

        Text(
            text = "Highball Glass",
        )

        Text(
            text = "Ingredients",
            fontWeight = FontWeight.Bold
        )

        Column() {
            Text(
                text = "50 ml White Rum",
            )
            Text(
                text = "25 ml Fresh Lime Juice",
            )
            Text(
                text = "2 teaspoons White Sugar",
            )
            Text(
                text = "6–8 Fresh Mint Leaves",
            )
            Text(
                text = "Soda Water",
            )
            Text(
                text = "Ice Cubes",
            )
        }

        Text(
            text = "Recipe",
            fontWeight = FontWeight.Bold
        )

        Column() {
            Text(
                text = "Place the mint leaves and sugar in the glass.",
            )
            Text(
                text = "Gently muddle to release the mint aroma",
            )
            Text(
                text = "Add fresh lime juice",
            )
            Text(
                text = "Fill the glass with ice cubes",
            )
            Text(
                text = "Pour in the white rum",
            )
            Text(
                text = "Top up with soda water",
            )
            Text(
                text = "Stir gently to combine",
            )
            Text(
                text = "Garnish with a mint sprig and a lime wedge",

            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopAppBarExample(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Medium Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // add later
        }
    }
}