package fr.isen.turcheschi.thegreatestcocktailapp.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.TabBarItem

@Composable
fun BottomAppBar(items:List<TabBarItem>, navController: NavController)
{
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar (containerColor = colorResource(R.color.sunset_orange)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(item.title)
                },
                icon = {
                    TabBarIcon(
                        selectedTabIndex == index,
                        item.selectedIcon,
                        item.unselectedIcon,
                        item.title
                    )
                },
                label = { Text(item.title) },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.ocean_blue),
                    selectedTextColor = colorResource(R.color.text_dark),
                    unselectedIconColor = colorResource(R.color.ocean_blue),
                    unselectedTextColor = colorResource(R.color.text_dark)
                )
            )
        }
    }
}

@Composable
fun TabBarIcon(isSelected: Boolean, selectedIcon: ImageVector, unSelectedIcon: ImageVector, title: String)
{
    Icon(
        if(isSelected) selectedIcon else unSelectedIcon,
        title
    )
}