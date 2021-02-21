buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.android.tools.build:gradle:4.1.2")
    }
}

group = "me.pagel"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
    }
}