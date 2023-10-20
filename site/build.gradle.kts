import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script
import java.util.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.serialization.plugin)
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

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
            head.add {
                script {
                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
                }

                link {
                    rel = "stylesheet"
                    href = "/styles/github-dark.css"
                }

                script {
                    src = "/scripts/highlight.min.js"
                }
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("blogmultiplatform", includeServer = true)

    sourceSets {

        val jsMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
                implementation(libs.kotlinx.serialization)
                implementation(libs.ballast.core)
                implementation(libs.ballast.saved.state)
                implementation(libs.markdown)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.js)
                implementation(project(":common"))
                // implementation(libs.kobwebx.markdown)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(libs.kobweb.api)
                implementation(libs.kotlin.mongodb)
                implementation(libs.kotlinx.serialization)
                implementation(libs.ktor.content.negotiation)
                implementation(project(":common"))
            }
        }
    }
}
