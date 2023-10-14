package org.example.blogmultiplatform.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.core.Page
import org.example.blogmultiplatform.components.sections.home.*
import org.example.blogmultiplatform.modules.home.HomePageViewModel


@Page
@Composable
fun HomePage() {
    val scope = rememberCoroutineScope()
    val viewModel = remember { HomePageViewModel(scope) }
    SitePageLayout {
        HomeHeroSection(viewModel)
        LatestPostsSection(viewModel)
        SponsoredPostsSection(viewModel)
        PopularPostsSection(viewModel)
        NewsLetterSection(viewModel)
    }
}

