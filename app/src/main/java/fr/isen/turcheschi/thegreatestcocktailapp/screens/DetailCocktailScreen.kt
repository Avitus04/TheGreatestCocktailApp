package fr.isen.turcheschi.thegreatestcocktailapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.models.Category
import fr.isen.turcheschi.thegreatestcocktailapp.network.CocktailViewModel

@Composable
fun DetailCocktailScreen(
    modifier: Modifier = Modifier,
    viewModel: CocktailViewModel = viewModel()
) {

    val cocktail by viewModel.cocktail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRandomCocktail()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.purple_500),
                        colorResource((R.color.purple_700))
                    )
                )
            ).fillMaxSize()
        )
        {
            Column(
                modifier = modifier.fillMaxSize().verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //MediumTopAppBarExample(modifier)
                Image(
                    painter = painterResource(id = R.drawable.cocktail_intro),
                    contentDescription = "An image of a yoghurt cocktail",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp, color = colorResource(R.color.teal_200),
                            CircleShape
                        )
                )

                Text(
                    text = "Yoghurt Cooler",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = colorResource(
                        R.color.white
                    ),
                    modifier = Modifier.padding(vertical = 25.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryView(Category.OTHER)
                    CategoryView(Category.NON_ALCOHOLIC)
                }

                Row() {
//                Icon(
//                    painter = painterResource(R.drawable.icon_glass_gen),
//                    contentDescription = "Icon of a tag",
//                )
                    Text(
                        text = "Highball Glass",
                        color = colorResource(R.color.grey_400),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }

                Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Column(
                        Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        colorResource(R.color.grey_600),
                                        colorResource(R.color.grey_700)
                                    )
                                )
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Ingredients",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = colorResource(
                                R.color.white
                            )
                        )
                        Row()
                        {
                            Text(text = "Yoghurt", color = colorResource(R.color.white))
                            Text(text = "1 cup", color = colorResource(R.color.white))
                        }
                        Row() {
                            Text(text = "Fruit", color = colorResource(R.color.white))
                            Text(text = "1 cup", color = colorResource(R.color.white))
                        }
                    }
                }

                Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Column(
                        Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        colorResource(R.color.grey_600),
                                        colorResource(R.color.grey_700)
                                    )
                                )
                            )
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Recipe",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = colorResource(
                                R.color.white
                            )
                        )
                        Text(text = "Poor limon", color = colorResource(R.color.white))
                        Text(text = "Step 2", color = colorResource(R.color.white))
                        Text(text = "Step 3", color = colorResource(R.color.white))

                    }
                }


                Row(modifier = modifier) {
                    Text(
                        text = "A la une",
                        modifier = Modifier.padding(end = 8.dp),
                        color = colorResource(
                            R.color.white
                        )
                    )
                    Text(
                        text = "Categories",
                        modifier = Modifier.padding(end = 8.dp),
                        color = colorResource(
                            R.color.white
                        )
                    )
                    Text(text = "Favoris", color = colorResource(R.color.white))
                }
            }
        }

        if (cocktail == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = cocktail?.strDrink ?: "",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = cocktail?.strInstructions ?: "",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
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
            color = colorResource(R.color.white),
        )
    }
}