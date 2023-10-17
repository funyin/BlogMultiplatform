plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.serialization.plugin)
}

group = "org.example.blogmultiplatform"
version = "1.0-SNAPSHOT"

kotlin {
    js(IR){ browser()}
    jvm()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization)
                implementation(libs.ballast.core)
                implementation(libs.ballast.saved.state)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.content.negotiation)
            }
        }

        val jsMain by getting {
            dependencies {
//                implementation("org.jetbrains.compose.runtime:runtime:1.5.1")
                implementation(compose.html.core)
//                implementation(libs.kobweb.core)
//                implementation(libs.kobweb.silk.core)
//                implementation(libs.kobweb.silk.icons.fa)
                implementation(libs.kotlinx.serialization)
//                implementation(libs.ballast.saved.state)
//                implementation(libs.markdown)
//                implementation(libs.kotlinx.datetime)
//                implementation(libs.ktor.client.js)
                // implementation(libs.kobwebx.markdown)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kobweb.api)
                implementation(libs.kotlin.mongodb)
                implementation(libs.kotlinx.serialization)
            }
        }
    }
}
