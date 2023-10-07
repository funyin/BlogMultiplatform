package org.example.blogmultiplatform.res

val Res.Routes.Companion.login: String
    get() = "/login"
val Res.Routes.Companion.adminHome: String
    get() = "/admin/"
val Res.Routes.Companion.createPost: String
    get() = "${posts}createpost"
val Res.Routes.Companion.posts: String
    get() = "${adminHome}posts/"
val Res.Routes.Companion.postSuccess: String
    get() = "${posts}success"