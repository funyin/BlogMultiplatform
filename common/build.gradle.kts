plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.serialization.plugin)
    id("com.android.library")
}

group = "org.example.blogmultiplatform"
version = "1.0-SNAPSHOT"

android {
    compileSdk = 34
//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    defaultConfig {
//        minSdk = 21
//    }
    namespace = "com.example.blogmultiplatform"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    js(IR) { browser() }
    jvm ()
    androidTarget()

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
                implementation(compose.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(libs.kotlinx.serialization)
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
