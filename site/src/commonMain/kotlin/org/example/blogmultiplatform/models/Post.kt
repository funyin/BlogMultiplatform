package org.example.blogmultiplatform.models

expect class Post {
    val id: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val content: String
    val category: String
    val popular: Boolean
    val main: Boolean
    val sponsored: Boolean
}