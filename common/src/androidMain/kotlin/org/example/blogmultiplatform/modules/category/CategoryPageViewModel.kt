package org.example.blogmultiplatform.modules.category

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.AndroidViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope
import org.example.blogmultiplatform.models.Category

class CategoryPageViewModel(
    coroutineScope: CoroutineScope,
    category: Category,
) : AndroidViewModel<
        CategoryPageContract.Inputs,
        CategoryPageContract.Events,
        CategoryPageContract.State>(
    coroutineScope = coroutineScope,
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
        }
        .withViewModel(
            inputHandler = CategoryPageInputHandler(),
            initialState = CategoryPageContract.State(category),
            name = "CategoryPage",
        )
        .build(),
)
