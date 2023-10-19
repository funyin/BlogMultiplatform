package org.example.blogmultiplatform.modules.category

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.flow.collectLatest
import org.example.blogmultiplatform.data.MongoSyncRepo
import org.example.blogmultiplatform.models.UiState

class CategoryPageInputHandler : InputHandler<
        CategoryPageContract.Inputs,
        CategoryPageContract.Events,
        CategoryPageContract.State> {
    override suspend fun InputHandlerScope<
            CategoryPageContract.Inputs,
            CategoryPageContract.Events,
            CategoryPageContract.State>.handleInput(
        input: CategoryPageContract.Inputs
    ): Unit = when (input) {
        CategoryPageContract.Inputs.GetItems -> {
            updateState { it.copy(state = UiState.Loading) }
            val category = getCurrentState().category
            sideJob(key = "getItems") {
                val mongoSyncRepo = MongoSyncRepo()
                mongoSyncRepo.searchByCategory(category).collectLatest {
                    postInput(CategoryPageContract.Inputs.GetItems.Response(response = it))
                }
            }
        }

        is CategoryPageContract.Inputs.GetItems.Response -> {
            updateState { it.copy(state = input.response) }
        }

    }
}
