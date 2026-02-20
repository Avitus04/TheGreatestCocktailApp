package fr.isen.turcheschi.thegreatestcocktailapp.screens

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBarDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.internal.NavContext
import fr.isen.turcheschi.thegreatestcocktailapp.R
import fr.isen.turcheschi.thegreatestcocktailapp.TabBarItem

@Composable
fun BottomAppBar(items:List<TabBarItem>, navController: NavController)
{
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selectedTabIndex == index,
                {
                    selectedTabIndex = index
                    navController.navigate(item.title)
                },
            {
                TabBarIcon(
                    selectedTabIndex == index,
                    item.selectedIcon,
                    item.unselectedIcon,
                    item.title
                )
            },
            label = {Text(item.title)}
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