package org.example.blogmultiplatform.res

import org.example.blogmultiplatform.common.BuildConfig

actual val baseUrl: String
    get() = BuildConfig.MONGO_URI
//    update for android
//    get() = "http://10.0.2.2:8080"