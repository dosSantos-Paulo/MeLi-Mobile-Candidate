buildscript {
    dependencies {
        classpath(Plugins.crashlyticsGradle)
    }
}

plugins {
    java
    id(Plugins.androidApplication) version Versions.Core.agp apply false
    id(Plugins.kotlinAndroid) version Versions.Core.kotlin apply false
    id(Plugins.googleServices) version Versions.Google.googleServices apply false
    id(Plugins.jacoco)
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.test {
    useJUnitPlatform()
    jacoco {
        enabled = true
    }
}