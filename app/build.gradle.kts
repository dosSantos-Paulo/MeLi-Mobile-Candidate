import java.util.Properties

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.googleServices)
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
        release {
            isMinifyEnabled = false
            android.buildFeatures.buildConfig = true

            proguardFiles(
                getDefaultProguardFile(Dependencies.Commons.defaultProGuardFile),
                Dependencies.Commons.proGuardRulerPro
            )

            getLocalProperties(Dependencies.Firebase.firebaseKey)?.let {key ->
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
}

dependencies {
    /**
     * Core
     */
    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.Core.appcompat)
    implementation(Dependencies.Core.appcompat)
    implementation(Dependencies.Core.material)
    implementation(Dependencies.Core.activity)
    implementation(Dependencies.Core.constraintlayout)

    /**
     * Firebase
     */
//    implementation(platform(Dependencies.Firebase.bom))
//    implementation(Dependencies.Firebase.analytics)

    /**
     * Test
     */
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidxJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)
}

fun getLocalProperties(key: String): String? {
    val properties = Properties()
    properties.load(project.rootProject.file(Dependencies.Commons.localProperties).inputStream())
    return properties.getProperty(key)
}

fun updateFirebaseApi(key: String) {
    val jsonFilePath = Dependencies.Commons.googleServicesJsonPath
    val jsonFile = File(jsonFilePath)
    val jsonContent = jsonFile.readText()

    jsonFile.writeText(
        jsonContent.replace(
            "\"current_key\": \"wrong_key\"",
            "\"current_key\": $key"
        )
    )
}