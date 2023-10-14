package org.example.blogmultiplatform.data

import com.mongodb.MongoWriteException
import org.example.blogmultiplatform.models.Subscriber

object SubscribersController {

    suspend fun ApiController.addSubscriber(email: String): Boolean {
        try {
            return subscribersCollection.insertOne(Subscriber(id = email)).wasAcknowledged()
        }catch (e:MongoWriteException){
            throw Exception("Email already added")
        }
    }
}