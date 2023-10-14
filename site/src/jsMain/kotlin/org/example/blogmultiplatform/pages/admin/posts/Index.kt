package org.example.blogmultiplatform.pages.admin.posts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import org.example.blogmultiplatform.components.layouts.AdminPageLayout
import org.example.blogmultiplatform.components.widgets.*
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.posts.posts.PostsUIState
import org.example.blogmultiplatform.modules.posts.posts.PostsViewModel
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.createPost
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun PostsPage() {
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val viewModel = remember {
        PostsViewModel(
            scope, initialState = PostsUIState(
                searchValue = context.route.params["search"]
                    ?: ""
            )
        )
    }
    LaunchedEffect(Unit) {
        viewModel.showMore(10)
    }
    val uiState by viewModel.uiState.collectAsState()
    val postsState = uiState.postsState
    val deletePostsState = uiState.deletePostsState
    AdminPageLayout {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 50.px, leftRight = 10.percent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!uiState.selectMode)
                SearchInput(
                    modifier = Modifier
                        .thenIf(uiState.selectMode, Modifier.visibility(Visibility.Hidden))
                        .transition(CSSTransition(TransitionProperty.of("visibility"), duration = 200.ms)),
                    value = uiState.searchValue
                ) {
                    viewModel.postState(uiState.copy(searchValue = it))
                }
            Row(
                modifier = Modifier.fillMaxWidth().margin(top = 24.px), verticalAlignment = Alignment.CenterVertically
            ) {
                SwitchTile(
                    switchSize = SwitchSize.LG,
                    text = if (!uiState.selectMode) "Select" else "${uiState.selectedPosts.size} Posts Selected",
                    checked = uiState.selectMode
                ) {
                    viewModel.postState(uiState.copy(selectMode = it))
                }
                Spacer()
                AppButton(
                    text = "Delete", modifier = Modifier.width(100.px).background(AppColors.Red.rgb)
                        .visibility(Visibility.Hidden)
                        .thenIf(
                            uiState.selectMode && uiState.selectedPosts.isNotEmpty(),
                            Modifier.visibility(Visibility.Visible)
                        )
                ) {
                    viewModel.deletePosts()
                }
            }
            when (postsState) {
                is UiState.Error -> AppErrorView(text = postsState.errorMessage) {
                    viewModel.showMore()
                }

                UiState.Initial,
                UiState.Loading -> Spinner()

                is UiState.Success -> {
                    val numColumns = remember { numColumns(base = 1, sm = 2, md = 3, lg = 4) }
                    if (postsState.data.isEmpty())
                        AppEmptyView(text = "No Posts Found")
                    else
                        SimpleGrid(
                            numColumns = numColumns,
                            modifier = Modifier.fillMaxWidth().margin(top = 24.px)
                                .width(100.percent)
                                .columnGap(20.px)
                                .rowGap(20.px)
                        ) {
                            postsState.data.forEachIndexed { index, postLight ->
                                PostPreview(
                                    postLight = postLight,
                                    selectable = uiState.selectMode,
                                    checked = uiState.selectedPosts.any { it == postLight.id },
                                    onCheckChanged = {
                                        if (it) {
                                            viewModel.postState(
                                                uiState.copy(
                                                    selectedPosts = arrayOf(
                                                        *uiState.selectedPosts.toTypedArray(),
                                                        postLight.id
                                                    ).toList()
                                                )
                                            )
                                        } else {
                                            val list = uiState.selectedPosts.toMutableList()
                                            list.removeAll { it == postLight.id }
                                            viewModel.postState(
                                                uiState.copy(
                                                    selectedPosts = list,
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    context.router.navigateTo(Res.Routes.createPost(postId = postLight.id))
                                }
                            }
                        }

                    ShowMoreButton(uiState = uiState.showMoreState) {
                        viewModel.showMore(10)
                    }
                }
            }

        }
    }
    if (deletePostsState is UiState.Error)
        MessagePopup(message = deletePostsState.errorMessage) {
            viewModel.postState(uiState.copy(deletePostsState = UiState.Initial))
        }
}

