package org.example.blogmultiplatform.data

import com.example.blogmultiplatform.BuildConfig
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

class MongoSyncRepo {
    private val app = App.create(BuildConfig.RealmAppId)
    private val user = app.currentUser
    private lateinit var realm: Realm

    companion object {
        suspend fun init() {
            App.create(BuildConfig.RealmAppId).login(Credentials.anonymous())
        }
    }

    private fun configRealm(user: User) {
        val config = SyncConfiguration.Builder(user, setOf(PostLight::class))
            .initialSubscriptions {
                add(query = it.query<PostLight>(), name = "Blog Posts")
            }
            .log(LogLevel.ALL)
            .build()
        realm = Realm.open(config)
    }

    fun readAllPost(): Flow<UiState<List<PostLight>>> {
        return user?.let {
            try {
                configRealm(it)
                realm.query<PostLight>()
                    .asFlow()
                    .map {
                        val data = it.list.toList()
                        UiState.Success(data)
                    }
            } catch (e: Throwable) {
                flow { emit(UiState.Error(e.message.toString())) }
            }
        } ?: run {
            flow { emit(UiState.Error("User not authenticated")) }
        }
    }
}