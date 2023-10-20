import java.util.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.serialization.plugin)
    alias(libs.plugins.mongodb.realm)
    id("com.android.library")
    id("com.github.gmazzo.buildconfig")
}

group = "org.example.blogmultiplatform"
version = "1.0-SNAPSHOT"


val properties = Properties()
val propertiesFile = project.rootProject.file("local.properties")
if (propertiesFile.exists()) {
    properties.load(propertiesFile.inputStream())
}
buildConfig {
    properties.forEach {
        if ("${it.key}" != "sdk.dir") {
            buildConfigField("String", it.key.toString(), "\"${it.value}\"")
        }
    }
}

android {
    compileSdk = 34
//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes{
        buildTypes.all {
            buildConfigField(type = "String", name = "RealmAppId", value = "\"application-0-hoehk\"")
        }
    }
    namespace = "com.example.blogmultiplatform"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
                implementation(libs.ballast.core)
                implementation(libs.ballast.saved.state)
                implementation(libs.ktor.client.android)
                implementation(libs.mongodb.sync)

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
