package com.example.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android.pages.HomePage

@Composable
fun SetUpNavGraph(controller:NavHostController) {
    NavHost(navController = controller, startDestination = Page.Home.route){
        composable(route = Page.Home.route){
            HomePage()
        }
        composable(route = Page.Category.route){}
        composable(route = Page.Details.route){}
    }
}