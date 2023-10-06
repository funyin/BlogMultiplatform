package org.example.blogmultiplatform.data

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.firstOrNull
import org.example.blogmultiplatform.core.Properties.dbName
import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.User
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@InitApi
fun initMongoDb(context: InitApiContext) {
    context.data.add(MongoController(context))
}

class MongoController(private val context: InitApiContext) {
    private val client = MongoClient.create("mongodb://localhost:27017")
    private val database = client.getDatabase(dbName)
    private val usersCollection = database.getCollection<User>("users")
    private val postsCollection = database.getCollection<Post>("posts")
    suspend fun login(request: LoginRequest): User? {
        return try {
            usersCollection.find(
                and(
                    eq(User::userName.name, request.userName),
                    eq(User::password.name, hashPassword(request.password))
                ),
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }

    suspend fun checkUserId(userId: String): Boolean {
        return try {
            val documentCount = usersCollection.countDocuments(eq("_id", userId))
            return documentCount > 0
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            false
        }
    }

    private fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
        val hexString = StringBuffer()

        for (byte in hashBytes) {
            hexString.append(String.format("%02x", byte))
        }
        return hexString.toString()
    }

    suspend fun addPost(post: Post): Boolean {
        return postsCollection.insertOne(post).wasAcknowledged()
    }
}