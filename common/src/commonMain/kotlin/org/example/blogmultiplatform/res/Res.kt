package org.example.blogmultiplatform.res

expect val baseUrl :String
object CommonRes {
    object Strings {
        val apiBaseUrl: String
            get() = "$baseUrl/api/"
    }
}