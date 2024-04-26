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
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files).apply {
            println("jacoco is here -> ${this.asPath}")
        })
    }
}