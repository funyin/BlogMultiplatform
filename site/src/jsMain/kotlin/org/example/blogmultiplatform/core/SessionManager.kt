package org.example.blogmultiplatform.core

import kotlinx.browser.localStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.RandomJoke
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.createPost.CreatePostContract
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

object SessionManager {
    fun startSession(user: User) {
        localStorage[Res.Strings.StoreKeys.user] = Json.encodeToString(user)
    }

    fun endSession() {
        localStorage.removeItem(Res.Strings.StoreKeys.user)
    }

    fun setCreatePostState(state: String) {
        localStorage[Res.Strings.StoreKeys.createPostState] = state
    }

    fun getCreatePostState(): String {
        return localStorage.getItem(Res.Strings.StoreKeys.createPostState)
            ?: Json.encodeToString(CreatePostContract.State())
    }


    var jokeLastFetchDate: Date?
        get() {
            return localStorage[Res.Strings.StoreKeys.lastJokeFetchDate]?.let {
                Date(dateString = it)
            }
        }
        private set(value) {
            if (value == null)
                localStorage.removeItem(Res.Strings.StoreKeys.lastJokeFetchDate)
            else
                localStorage[Res.Strings.StoreKeys.lastJokeFetchDate] = value.toDateString()
        }

    var lastFetchedJoke: RandomJoke?
        get() {
            return localStorage[Res.Strings.StoreKeys.lastJoke]?.let {
                Json.decodeFromString<RandomJoke>(it)
            }
        }
        set(value) {
            if (value != null) {
                localStorage[Res.Strings.StoreKeys.lastJoke] = Json.encodeToString(value)
                jokeLastFetchDate = Date()
            } else
                localStorage.removeItem(Res.Strings.StoreKeys.lastJoke)
        }
}