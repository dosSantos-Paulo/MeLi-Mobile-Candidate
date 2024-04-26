buildscript {
    dependencies {
        classpath(Plugins.crashlyticsGradle)
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.6")
    }
}

plugins {
    java
    id(Plugins.androidApplication) version Versions.Core.agp apply false
    id(Plugins.kotlinAndroid) version Versions.Core.kotlin apply false
    id(Plugins.googleServices) version Versions.Google.googleServices apply false
    id(Plugins.jacoco)
    kotlin("jvm") version "1.5.31"
}

jacoco {
    toolVersion = "0.8.8"
}

val qodanaToken: String by project
val javaVersion: String by project

tasks {
    val build by getting {
        // Configuração para armazenar em cache as dependências Gradle
        outputs.upToDateWhen { false }
        doLast {
            // Executa a tarefa de build normalmente
            exec {
                commandLine = listOf("./gradlew", "build")
            }
        }
    }

    val qodana by registering {
        dependsOn("build") // Garante que a tarefa de build seja concluída antes de executar o Qodana Scan
        doLast {
            // Executa a tarefa de Qodana Scan
            exec {
                commandLine = listOf("comando_para_executar_o_Qodana_Scan") // Substitua pelo comando real
            }
        }
    }
}

// Configuração para cache de dependências Gradle

@Suppress("UnstableApiUsage")
configurations {
    create("gradleCache")
}

dependencies {
    "gradleCache"("com.github.ben-manes.gradleplugins:gradle-versions-plugin:0.39.0")
}

tasks.register("restoreCache") {
    dependsOn("assemble")
    doLast {
        exec {
            commandLine = listOf("gradle", "dependencies", "--configuration", "gradleCache")
        }
    }
}

tasks.register("saveCache") {
    dependsOn("assemble")
    doLast {
        exec {
            commandLine = listOf("gradle", "dependencies", "--configuration", "gradleCache", "--write-to-file", "gradle_cache.txt")
        }
    }
}

tasks.getByName("build") {
    dependsOn("restoreCache")
    finalizedBy("saveCache")
}

tasks.jacocoTestReport<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files).apply {
            println("jacoco is here -> ${this.asPath}")
        })
    }
}