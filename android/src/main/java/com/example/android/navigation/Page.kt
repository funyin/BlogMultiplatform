package com.example.android.navigation

sealed class Page(val route: String) {
    data object Home : Page(route = "home_page")
    data class Category(val category: org.example.blogmultiplatform.models.Category) :
        Page(route = "category_page/${category.name}") {
        companion object {
            const val route = "category_page/{category}"
        }
    }

    data class Details(val postId: String) : Page(route = "details_page/$postId") {
        companion object {
            const val route = "details_page/{postId}"
        }
    }
}