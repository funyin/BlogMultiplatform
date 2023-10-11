package org.example.blogmultiplatform.pages.admin.posts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgb
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.datetime.Instant
import kotlinx.datetime.toJSDate
import org.example.blogmultiplatform.components.layouts.AdminPageLayout
import org.example.blogmultiplatform.components.widgets.*
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.posts.posts.PostsViewModel
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Progress

@Page
@Composable
fun PostsPage() {
    val scope = rememberCoroutineScope()
    val viewModel = remember { PostsViewModel(scope) }
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
            SearchInput(value = uiState.searchValue) {
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
                is UiState.Error -> SpanText(postsState.errorMessage)
                UiState.Initial,
                UiState.Loading -> Spinner()

                is UiState.Success -> {
                    val numColumns = remember { numColumns(base = 1, sm = 2, md = 3, lg = 4) }
                    SimpleGrid(
                        numColumns = numColumns,
                        modifier = Modifier.fillMaxWidth().margin(top = 24.px).width(100.percent)
                    ) {
                        postsState.getData.forEachIndexed { index, postLight ->
                            PostPreview(
                                modifier = numColumns.itemSpace(
                                    index = index,
                                    itemCount = postsState.getData.size,
                                    horizontalGap = 20.px,
                                    verticalGap = 20.px
                                ),
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

@Composable
private fun ShowMoreButton(uiState: UiState<Any>, onClick: () -> Unit = {}) {
    val modifier = Modifier.margin(topBottom = 50.px)
    if (uiState.isLoading)
        Progress(
            attrs = Modifier.width(200.px)
                .then(modifier)
                .toAttrs()
        )
    else
        SpanText("Show More", modifier = Modifier.fontSize(16.px).then(modifier)
            .cursor(Cursor.Pointer)
            .fontWeight(FontWeight.Bold)
            .onClick {
                onClick()
            }
            .thenIf(uiState.isInitial, Modifier.visibility(Visibility.Hidden))
        )
}

@Composable
private fun PostPreview(
    modifier: Modifier,
    postLight: PostLight,
    selectable: Boolean,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.then(modifier)
            .thenIf(
                selectable, Modifier.padding(20.px)
                    .border(
                        width = 2.px,
                        color = if (checked) AppColors.Primary.rgb else rgb(0xEDEDED),
                        style = LineStyle.Solid
                    )
                    .borderRadius(4.px)
            )
            .cursor(Cursor.Pointer)
            .transition(
                CSSTransition(TransitionProperty.of("border"), duration = 300.ms),
                CSSTransition(TransitionProperty.of("padding"), duration = 500.ms)
            )
            .onClick {
                if (selectable)
                    onCheckChanged(!checked)
                else
                    onClick()
            }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(320.px)
                .background(AppColors.LightGrey.rgb)
        )
        Column(modifier = modifier.fillMaxWidth().margin(top = 16.px), horizontalAlignment = Alignment.Start) {
            SpanText(
                text = Instant.fromEpochMilliseconds(postLight.date).toJSDate()
                    .toLocaleDateString(),
                modifier = Modifier.fontSize(10.px).color(color = rgb(0x7a7a7a))
            )
            SpanText(
                text = postLight.title,
                modifier = Modifier.color(Colors.Black).fontSize(20.px)
                    .fontWeight(FontWeight.Bold)
                    .maxLines(2)
            )
            SpanText(
                text = postLight.subtitle,
                modifier = Modifier.maxLines(2)
            )
            Row(
                modifier = Modifier.fillMaxWidth().margin(top = 8.px),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText(
                    text = postLight.category,
                    modifier = Modifier.padding(topBottom = 9.px, leftRight = 14.px)
                        .border(
                            width = 0.86.px,
                            style = LineStyle.Solid,
                            color = rgb(0x7A7A7A)
                        )
                        .borderRadius(55.px)
                        .color(rgb(0x7A7A7A))
                        .fontSize(12.px)
                )
                if (selectable)
                    Checkbox(
                        checked = checked,
                        size = CheckboxSize.LG, onCheckedChange = onCheckChanged
                    )
            }
        }
    }
}