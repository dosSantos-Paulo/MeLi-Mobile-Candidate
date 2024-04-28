object Dependencies {
    object Commons {
        const val nameSpace = "com.dossantos.melimobilecandidate"
        const val nameSpaceDesignsystem = "com.dossantos.designsystem"
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val defaultProGuardFile = "proguard-android-optimize.txt"
        const val proGuardRulerPro = "proguard-rules.pro"
        const val localProperties = "local.properties"
        const val googleServicesJsonPath = "app/google-services.json"
    }
    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val androidxJunit = "androidx.test.ext:junit:${Versions.Test.junitVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Test.espressoCore}"
        const val koin = "io.insert-koin:koin-test"
    }

    object Core {
        const val coreKtx = "androidx.core:core-ktx:${Versions.Core.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.Core.appcompat}"
        const val material = "com.google.android.material:material:${Versions.Core.material}"
        const val activity = "androidx.activity:activity:${Versions.Core.activity}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.Core.constraintlayout}"
    }

    object Firebase {
        const val firebaseKey = "FIREBASE_KEY"
        const val bom = "com.google.firebase:firebase-bom:${Versions.Firebase.bom}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
    }

    object Koin {
        const val bom = "io.insert-koin:koin-bom:${Versions.Koin.bom}"
        const val core = "io.insert-koin:koin-core"
        const val android = "io.insert-koin:koin-android"
    }
}