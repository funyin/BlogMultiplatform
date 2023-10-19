package com.example.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android.pages.CategoryPage
import com.example.android.pages.DetailPage
import com.example.android.pages.HomePage
import com.example.android.ui.theme.BlogMultiplatformTheme
import org.example.blogmultiplatform.models.Category

@Composable
fun SetUpNavGraph(controller: NavHostController) {
    BlogMultiplatformTheme {
        NavHost(navController = controller, startDestination = Page.Home.route) {
            composable(route = Page.Home.route) {
                HomePage(navController = controller)
            }
            composable(route = Page.Category.route, arguments = listOf(
                navArgument(name = "category") {
                    type = NavType.StringType
                }
            )) {
                val category = Category.valueOf(it.arguments!!.getString("category")!!)
                CategoryPage(navcontroller=controller, category = category)
            }
            composable(route = Page.Details.route) {
                DetailPage(navcontroller=controller)
            }
        }
    }
}