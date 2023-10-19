package com.example.android.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.components.PostCard
import com.example.android.ui.theme.BlogMultiplatformTheme
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.ui.home.HomePageContract.Inputs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val viewModel: HomePageViewModel = remember(scope) { HomePageViewModel(scope) }
    LaunchedEffect(Unit) {
        viewModel.trySend(Inputs.GetAllPosts)
    }
    val uiState by viewModel.observeStates().collectAsState()
    val allPostsState = uiState.allPosts
    val searchPostsState = uiState.searchResponse
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
                    viewModel.trySend(Inputs.ShowSearchBar(true))
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "menu")
                }
            })
            if (uiState.showSearchBar)
                SearchBar(
                    query = uiState.searchValue,
                    onQueryChange = {
                        viewModel.trySend(Inputs.SearchChanged(it))
                    },
                    onSearch = {
                        viewModel.trySend(Inputs.SearchPostsPosts(it))
                    },
                    active = true,
                    onActiveChange = {},
                    leadingIcon = {
                        IconButton(onClick = {
                            viewModel.trySend(Inputs.ShowSearchBar(false))
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "menu")
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.trySend(Inputs.SearchChanged(""))
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                ) {
                    PostsContent(postsState = searchPostsState)
                }
        }) {
            PostsContent(modifier = Modifier.padding(it), postsState = allPostsState)
        }
    }
}

@Composable
private fun PostsContent(modifier: Modifier = Modifier, postsState: UiState<List<PostLight>>) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (postsState) {
            is UiState.Error -> {
                Text(text = postsState.errorMessage, textAlign = TextAlign.Center)
            }

            UiState.Initial -> {}
            UiState.Loading -> CircularProgressIndicator()

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    items(postsState.data) {
                        PostCard(post = it) {

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