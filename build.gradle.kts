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

tasks.withType<JacocoCoverageVerification> {
    violationRules {
        rule {
            limit {
                minimum = BigDecimal(0.62)
            }
        }
    }

    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(jacocoExclusions)
            }
        }))
    }
}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(jacocoExclusions)
            }
            println("jacoco report is here -> ${it.absolutePath}")
        }))
    }
}