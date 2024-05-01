plugins {
    id(Plugins.androidLib)
    id(Plugins.kotlinAndroid)
    id(Plugins.detekt)
    id(Plugins.kpta)
}

android {
    namespace = Dependencies.Commons.nameSpaceDesignsystem
    compileSdk = Versions.Commons.compileSdk

    defaultConfig {
        minSdk = Versions.Commons.minSdk

        testInstrumentationRunner = Dependencies.Commons.instrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Dependencies.Commons.defaultProGuardFile),
                Dependencies.Commons.proGuardRulerPro
            )
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
    implementation(project(Modules.domain))

    /**
     * Core
     */
    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.Core.liveData)
    implementation(Dependencies.Commons.timber)

    /**
     * Coroutines
     */
    implementation(Dependencies.Coroutines.core)

    /**
     * Retrofit
     */
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gson)
    implementation(Dependencies.Retrofit.interceptor)

    /**
     * Room
     */
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.compiler)

    /**
     * Test
     */
    implementation(Dependencies.Test.testCore)
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockK)
    testImplementation(Dependencies.Test.roboletrics)
    testImplementation(Dependencies.Test.archCore)
    androidTestImplementation(Dependencies.Test.androidxJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)
    compileOnly(Dependencies.Test.stdLib)
    testImplementation(Dependencies.Test.stdLib)
}