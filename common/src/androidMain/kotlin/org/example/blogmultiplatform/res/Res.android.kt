package org.example.blogmultiplatform.res

import org.example.blogmultiplatform.common.BuildConfig

actual val baseUrl: String
    get() = BuildConfig.BASE_URL
//    get() = "http://10.9.2.2:8080"