import java.util.Properties

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.googleServices)
    id(Plugins.crashlytics)
    id(Plugins.detekt)
    id(Plugins.kpta)
}

android {
    namespace = Dependencies.Commons.nameSpace
    compileSdk = Versions.Commons.compileSdk

    defaultConfig {
        applicationId = Dependencies.Commons.nameSpace
        minSdk = Versions.Commons.minSdk
        targetSdk = Versions.Commons.compileSdk
        versionCode = Versions.Commons.versionCode
        versionName = Versions.Commons.versionName

        testInstrumentationRunner = Dependencies.Commons.instrumentationRunner
    }

    buildTypes {
        debug {
            getSecret(Dependencies.Firebase.firebaseKey)?.let { key ->
                protectGoogleServicesToUpdates()
                updateFirebaseApi(key)
            }
        }

        release {
            isMinifyEnabled = false
            android.buildFeatures.buildConfig = true

            proguardFiles(
                getDefaultProguardFile(Dependencies.Commons.defaultProGuardFile),
                Dependencies.Commons.proGuardRulerPro
            )

            getSecret(Dependencies.Firebase.firebaseKey)?.let { key ->
                protectGoogleServicesToUpdates()
                updateFirebaseApi(key)
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    /**
     * Modules
     */
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(project(Modules.designSystem))

    /**
     * Core
     */
    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.Core.appcompat)
    implementation(Dependencies.Core.material)
    implementation(Dependencies.Core.activity)
    implementation(Dependencies.Core.constraintlayout)

    /**
     * Depedency Injection
     */
    implementation(platform(Dependencies.Koin.bom))
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    /**
     * Glide
     */
    implementation(Dependencies.Commons.glide)

    /**
     * Firebase
     */
    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.crashlytics)
    implementation(Dependencies.Firebase.analytics)

    /**
     * Navigation
     */
    implementation(Dependencies.Navigation.core)
    implementation(Dependencies.Navigation.fragment)

    /**
     * Test
     */
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.koin)
    androidTestImplementation(Dependencies.Test.androidxJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)

    /**
     * Room
     */
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.compiler)
}

fun getSecret(key: String): String? {
    return try {
        val properties = Properties()
        properties.load(
            project.rootProject.file(Dependencies.Commons.localProperties).inputStream()
        )
        properties.getProperty(key)
    } catch (_: Exception) {
        System.getenv(Dependencies.Firebase.firebaseKey).also { firebaseKey ->
            println("${Dependencies.Firebase.firebaseKey} -> $firebaseKey")
        }
    }
}

fun updateFirebaseApi(key: String) {
    val jsonFilePath = Dependencies.Commons.googleServicesJsonPath
    val jsonFile = File(jsonFilePath)
    val jsonContent = jsonFile.readText()

    jsonFile.writeText(
        jsonContent.replace(
            "\"current_key\": \"wrong_api\"",
            "\"current_key\": $key"
        )
    )
}

fun protectGoogleServicesToUpdates() {
    println("Protecting google-services.json ......")
    val command = "git update-index --assume-unchanged app/google-services.json"
    val process = ProcessBuilder().command("bash", "-c", command).start()

    if (process.waitFor() == 0) println("google-services.json is now protected")
    else throw RuntimeException("we need to protect google-services to updates")
}

val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml")) // or "reports/detekt/merge.sarif"
}

subprojects {

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        finalizedBy(reportMerge)
    }

    reportMerge {
        input.from(tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().map { it.xmlReportFile }) // or .sarifReportFile
    }
}
