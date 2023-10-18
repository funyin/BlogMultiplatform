package com.example.android.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.components.PostCard
import com.example.android.ui.theme.BlogMultiplatformTheme
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.ui.home.HomePageContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val viewModel = viewModel {
        HomePageViewModel(scope)
    }
    LaunchedEffect(Unit) {
        viewModel.trySend(HomePageContract.Inputs.GetAllPosts)
    }
    val uiState by viewModel.observeStates().collectAsState()
    val allPostsState = uiState.allPosts
    BlogMultiplatformTheme {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Blog")
            }, navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                }
            }, actions = {
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "menu")
                }
            })
        }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (allPostsState) {
                    is UiState.Error -> {
                        Text(text = allPostsState.errorMessage, textAlign = TextAlign.Center)
                    }

                    UiState.Initial,
                    UiState.Loading -> CircularProgressIndicator()

                    is UiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
                        ) {
                            items(allPostsState.data) {
                                PostCard(post = it) {

                                }
                            }

                        }

                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage()
}