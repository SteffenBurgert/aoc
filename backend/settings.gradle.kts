rootProject.name = "backend"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val versionCheckPluginVersion: String by settings
    val kotlinAllopen: String by settings

    plugins {
        // Kotlin
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion

        // Spring
        id("org.springframework.boot") version springBootVersion

        // Analytics
        id("com.github.ben-manes.versions") version versionCheckPluginVersion

        // Kotlin plugin allopen
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinAllopen
    }
}