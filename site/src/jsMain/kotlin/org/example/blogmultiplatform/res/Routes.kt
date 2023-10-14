package org.example.blogmultiplatform.res

import io.ktor.http.*

val Res.Routes.Companion.login: String
    get() = "/login"
val Res.Routes.Companion.adminHome: String
    get() = "/admin/"

fun Res.Routes.Companion.createPost(postId: String? = null): String = "${posts}createpost".run {
    postId?.let {
        "$this/$postId"
    } ?: this
}

val Res.Routes.Companion.posts: String
    get() = "${adminHome}posts/"

fun Res.Routes.Companion.postSuccess(message: String? = null): String =
    "${posts}success${message?.let { "?message=${it.encodeURLParameter()}" } ?: ""}"

val Res.Routes.Companion.clientPosts: String
    get() = "/posts"

fun Res.Routes.Companion.post(postId: String): String = "$clientPosts/$postId"