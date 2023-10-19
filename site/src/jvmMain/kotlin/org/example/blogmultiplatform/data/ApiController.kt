package org.example.blogmultiplatform.data

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.example.blogmultiplatform.core.Properties.dbName
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.Subscriber
import org.example.blogmultiplatform.models.User

@InitApi
fun initMongoDb(context: InitApiContext) {
    context.data.add(ApiController(context))
}

class ApiController(internal val context: InitApiContext) {
    internal val client = MongoClient.create(
//        "mongodb://localhost:27017"
        "mongodb+srv://funyin:0QL6GEkj3kF1iM1F@atlascluster.dfnaifk.mongodb.net/?retryWrites=true&w=majority"
    )
    internal val database = client.getDatabase(dbName)
    internal val usersCollection = database.getCollection<User>("users")
    internal val postsCollection = database.getCollection<Post>("posts")
    internal val subscribersCollection = database.getCollection<Subscriber>("subscribers")

}