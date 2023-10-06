package org.example.blogmultiplatform.ui.createPost

import com.copperleaf.ballast.savedstate.RestoreStateScope
import com.copperleaf.ballast.savedstate.SaveStateScope
import com.copperleaf.ballast.savedstate.SavedStateAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CreatePostSavedStateAdapter(
    private val prefs: Prefs
) : SavedStateAdapter<
        CreatePostContract.Inputs,
        CreatePostContract.Events,
        CreatePostContract.State> {

    interface Prefs {
        var stateData: String
    }

    override suspend fun SaveStateScope<
            CreatePostContract.Inputs,
            CreatePostContract.Events,
            CreatePostContract.State>.save() {
        saveDiff({ this }) { state ->
            prefs.stateData = Json.encodeToString(state)
        }
    }

    override suspend fun RestoreStateScope<
            CreatePostContract.Inputs,
            CreatePostContract.Events,
            CreatePostContract.State>.restore(): CreatePostContract.State {
        return Json.decodeFromString<CreatePostContract.State>(prefs.stateData)
    }
}
