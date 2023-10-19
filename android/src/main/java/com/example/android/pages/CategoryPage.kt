package com.example.android.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.android.navigation.Page
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.modules.category.CategoryPageContract
import org.example.blogmultiplatform.modules.category.CategoryPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPage(category: Category, navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val viewModel = remember(scope) {
        CategoryPageViewModel(scope, category)
    }
    val uiState by viewModel.observeStates().collectAsState()
    LaunchedEffect(Unit) {
        viewModel.trySend(CategoryPageContract.Inputs.GetItems)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(text = category.name)
                })
        }
    ) {
        PostsContent(modifier = Modifier.padding(it), postsState = uiState.state, onRetry = {
            viewModel.trySend(CategoryPageContract.Inputs.GetItems)
        }) {
            navController.navigate(Page.Details(it).route)
        }
    }
}