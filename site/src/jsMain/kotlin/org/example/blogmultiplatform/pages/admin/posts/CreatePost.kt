package org.example.blogmultiplatform.pages.admin.posts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.ComponentVariant
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.components.layouts.AdminPageLayout
import org.example.blogmultiplatform.components.widgets.*
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.*
import org.example.blogmultiplatform.modules.posts.create.CreatePostScreenEventHandler
import org.example.blogmultiplatform.modules.posts.create.CreatePostViewModel
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.createPost.CreatePostContract
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLTextAreaElement

private val Res.Id.Companion.postContent: String
    get() = "content"
val postContentStyle by ComponentStyle {
    base {
        Modifier
    }
    cssRule(" pre") {
        Modifier.background(Color.rgb(0x0d1117))
            .padding(8.px)
            .borderRadius(4.px)
    }
}

@Page
@Composable
fun CreatePostPage() {
    val breakpoint = rememberBreakpoint()

    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val viewModel = remember(scope) {
        CreatePostViewModel(coroutineScope = scope, eventHandler = CreatePostScreenEventHandler(pageContext = context))
    }
    val uiState by viewModel.observeStates().collectAsState()
    val createPostState = uiState.createPostState
    LaunchedEffect(uiState.content) {
        val src = uiState.content
        val flavour = CommonMarkFlavourDescriptor()
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
        val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()
        document.getElementById(Res.Id.previewId)?.innerHTML = html
        launch {
            delay(100)
            js("hljs.highlightAll()") as? Unit
        }
    }
    AdminPageLayout {
        Column(
            modifier = Modifier.fillMaxSize().maxWidth(700.px).align(Alignment.Center)
                .padding(topBottom = 50.px, leftRight = 16.px),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleGrid(
                numColumns = numColumns(base = 2, sm = 3),
                variant = ComponentVariant.Empty,
                modifier = Modifier.thenIf(breakpoint < Breakpoint.SM, Modifier.fillMaxWidth())
            ) {
                SwitchTile(
                    text = "Popular", switchSize = SwitchSize.LG, checked = uiState.popular
                ) {
                    viewModel.trySend(CreatePostContract.Inputs.UpdatePopular(it))
                }
                SwitchTile(
                    text = "Main",
                    switchSize = SwitchSize.LG,
                    checked = uiState.main,
                ) {
                    viewModel.trySend(CreatePostContract.Inputs.UpdateMain(it))
                }
                SwitchTile(
                    text = "Sponsored",
                    switchSize = SwitchSize.LG,
                    checked = uiState.sponsored,
                    modifier = Modifier.margin(top = if (breakpoint < Breakpoint.SM) 14.px else 0.px)
                ) {
                    viewModel.trySend(CreatePostContract.Inputs.UpdateSponsored(it))
                }
            }
            CustomInputField(
                modifier = Modifier.margin(top = 20.px).fillMaxWidth().background(AppColors.LightGrey.rgb),
                inputType = InputType.Text, value = uiState.title,
                onTextChanged = {
                    viewModel.trySend(CreatePostContract.Inputs.UpdateTitle(it))
                },
                placeholder = "Title",
            )
            CustomInputField(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth().background(AppColors.LightGrey.rgb),
                inputType = InputType.Text,
                value = uiState.subtitle,
                onTextChanged = {
                    viewModel.trySend(CreatePostContract.Inputs.UpdateSubtitleTitle(it))
                },
                placeholder = "Subtitle",
            )
            AppDropDown(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth(),
                selectedItem = uiState.category,
                options = Category.entries
            ) {
                viewModel.trySend(CreatePostContract.Inputs.UpdateCategory(it))
            }
            SwitchTile(modifier = Modifier.margin(top = 14.px).alignSelf(AlignSelf.SelfStart),
                switchSize = SwitchSize.MD,
                checked = uiState.pasteImageUrl,
                text = "Paste an Image URL instead",
                onCheckChanged = {
                    viewModel.trySend(CreatePostContract.Inputs.UpdatePastImageUrl(it))
                })
            Row(modifier = Modifier.fillMaxWidth().margin(top = 14.px)) {
                CustomInputField(
                    modifier = Modifier.fillMaxWidth().background(AppColors.LightGrey.rgb)
                        .transition(CSSTransition(TransitionProperty.All, duration = 300.ms)).weight(1),
                    inputType = InputType.Url, value = uiState.imageUrl,
                    onTextChanged = {
                        viewModel.trySend(CreatePostContract.Inputs.ImageUrl(it))
                    },
                    readOnly = !uiState.pasteImageUrl,
                    placeholder = "Image Url",
                )
                if (!uiState.pasteImageUrl) AppButton(
                    modifier = Modifier.width(92.px).margin(left = 14.px), text = "Upload"
                ) {
                    document.loadDataUrlFromDisk(accept = "image/png, image/jpg") {
                        viewModel.trySend(CreatePostContract.Inputs.ImageUrl(filename))
                    }
                }
            }
            SimpleGrid(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth(), numColumns = numColumns(base = 1, sm = 2)
            ) {
                Row(
                    modifier = Modifier.height(54.px).borderRadius(4.px)
                        .thenIf(breakpoint >= Breakpoint.SM, Modifier.margin(right = 14.px))
                        .background(AppColors.LightGrey.rgb)
                        .transition(CSSTransition(TransitionProperty.of("opacity"), duration = 300.ms))
                        .thenIf(uiState.showPreview, Modifier.pointerEvents(PointerEvents.None).opacity(50.percent)),
                    horizontalArrangement = if (breakpoint < Breakpoint.SM) Arrangement.Center else Arrangement.Start
                ) {
                    for (key in EditorKey.entries) {
                        EditorKeyView(
                            icon = key.icon,
                            iconSize = if (key == EditorKey.CapsOff) 14.px else 18.px,
                            active = uiState.activeKeys.contains(key)
                        ) {
                            val areaElement = document.getElementById(Res.Id.postContent) as HTMLTextAreaElement
                            scope.launch {
                                val position = areaElement.selectionEnd
                                viewModel.sendAndAwaitCompletion(CreatePostContract.Inputs.ToggleEditorKey(key,
                                    selection = areaElement.selectionEnd?.let { areaElement.selectionStart?.until(it) }
                                        ?: (uiState.content.lastIndex until uiState.content.lastIndex)))
                                areaElement.focus()
                                val end = (position ?: uiState.content.lastIndex) + key.stylingCursorOffset
                                areaElement.setSelectionRange(start = end, end = end)
                            }
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (breakpoint >= Breakpoint.SM) Spacer()
                    AppButton(
                        modifier = Modifier.width(97.px).thenIf(breakpoint < Breakpoint.SM, Modifier.fillMaxWidth())
                            .margin(top = if (breakpoint >= Breakpoint.SM) 0.px else 14.px).thenIf(
                                uiState.showPreview,
                                Modifier.background(AppColors.HalfBlack.rgb).color(AppColors.HalfWhite.rgb)
                            ).thenUnless(
                                uiState.showPreview,
                                Modifier.background(AppColors.LightGrey.rgb).color(AppColors.HalfBlack.rgb)
                            ), text = if (uiState.showPreview) "Editor" else "Preview"
                    ) {
                        viewModel.trySend(CreatePostContract.Inputs.ToggleShowPreview)
                    }
                }
            }

            Box(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth().height(400.px)
                    .background(AppColors.LightGrey.rgb)
            ) {
                CustomTextArea(
                    modifier = Modifier.fillMaxSize().id(Res.Id.postContent).resize(Resize.None)
                        .background(AppColors.LightGrey.rgb)
                        .visibility(if (uiState.showPreview) Visibility.Hidden else Visibility.Visible),
                    value = uiState.content,
                    placeholder = "Type here..."
                ) {
                    viewModel.trySend(CreatePostContract.Inputs.UpdateContent(it))
                }
                Div(
                    attrs = Modifier.id(Res.Id.previewId).fillMaxSize().padding(leftRight = 20.px, topBottom = 16.px)
                        .visibility(if (uiState.showPreview) Visibility.Visible else Visibility.Hidden)
                        .overflow(Overflow.Auto).scrollBehavior(ScrollBehavior.Smooth)
                        .then(postContentStyle.toModifier())
                        .toAttrs()
                ) {
                }
            }
            AppButton(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth(),
                text = "Create",
                loading = createPostState.isLoading
            ) {
                viewModel.trySend(CreatePostContract.Inputs.CreatePost)
            }
        }
    }
    if (createPostState is UiState.Error) MessagePopup(message = createPostState.errorMessage) {
        viewModel.trySend(CreatePostContract.Inputs.ClosePopup)
    }
    if (uiState.showLinkPopup != null)
        LinkPopup(message = uiState.content.slice(uiState.showLinkPopup!!),
            onDialogDismissed = { viewModel.trySend(CreatePostContract.Inputs.CloseLinkPopup) }) { text, link ->
            viewModel.trySend(CreatePostContract.Inputs.CloseLinkPopup)
            viewModel.trySend(
                CreatePostContract.Inputs.UpdateContent(
                    uiState.content.replaceRange(
                        uiState.showLinkPopup!!, EditorKey.Link.controlStyle(
                            text, link
                        )
                    )
                )
            )
        }
    if (uiState.showImagePopup != null)
        LinkPopup(message = uiState.content.slice(uiState.showImagePopup!!),
            onDialogDismissed = { viewModel.trySend(CreatePostContract.Inputs.CloseImagePopup) }) { text, link ->
            viewModel.trySend(CreatePostContract.Inputs.CloseImagePopup)
            viewModel.trySend(
                CreatePostContract.Inputs.UpdateContent(
                    uiState.content.replaceRange(
                        uiState.showImagePopup!!, EditorKey.Image.controlStyle(
                            text, link
                        )
                    )
                )
            )
        }
}

private val Res.Id.Companion.previewId: String
    get() = "previewId"

val EditorKeyStyle by ComponentStyle {
    base {
        Modifier.background(Colors.Transparent)
            .transition(CSSTransition(TransitionProperty.of("background"), duration = 300.ms))
    }
    hover {
        Modifier.background(AppColors.Primary.rgb)
    }
}

@Composable
private fun EditorKeyView(icon: String, iconSize: CSSpxValue, active: Boolean, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.thenIf(active, Modifier.background(AppColors.Primary.rgb))
            .thenIf(!active, EditorKeyStyle.toModifier()).width(40.px).fillMaxHeight().padding(10.px).borderRadius(4.px)
            .transition(CSSTransition(TransitionProperty.of("background"), duration = 300.ms)).cursor(Cursor.Pointer)
            .onClick {
                onClick()
            }) {
        Image(
            src = icon,
            modifier = Modifier.size(iconSize).color(if (active) Colors.White else AppColors.Secondary.rgb)
                .transition(CSSTransition(TransitionProperty.of("color"), duration = 300.ms)),
        )
    }
}

