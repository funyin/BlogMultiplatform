package org.example.blogmultiplatform.data

import com.mongodb.client.model.Filters
import kotlinx.coroutines.flow.firstOrNull
import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.User
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object AuthController {
    suspend fun ApiController.login(request: LoginRequest): User? {
        return try {
            usersCollection.find(
                Filters.and(
                    Filters.eq(User::userName.name, request.userName),
                    Filters.eq(User::password.name, hashPassword(request.password))
                ),
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }

    suspend fun ApiController.checkUserId(userId: String): Boolean {
        return try {
            val documentCount = usersCollection.countDocuments(Filters.eq("_id", userId))
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
}