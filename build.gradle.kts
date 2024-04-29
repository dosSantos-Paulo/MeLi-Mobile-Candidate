buildscript {
    dependencies {
        classpath(Plugins.crashlyticsGradle)
        classpath(Plugins.detektGradle)
    }
}

plugins {
    id(Plugins.androidApplication) version Versions.Core.agp apply false
    id(Plugins.kotlinAndroid) version Versions.Core.kotlin apply false
    id(Plugins.googleServices) version Versions.Google.googleServices apply false
    id(Plugins.kotlinJvm) version Versions.Core.kotlin apply false
    id(Plugins.kpta) version Versions.Core.kpta
}