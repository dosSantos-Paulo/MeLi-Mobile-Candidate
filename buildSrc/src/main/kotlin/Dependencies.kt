object Dependencies {
    object Commons {
        const val nameSpace = "com.dossantos.melimobilecandidate"
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val defaultProGuardFile = "proguard-android-optimize.txt"
        const val proGuardRulerPro = "proguard-rules.pro"
    }
    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val androidxJunit = "androidx.test.ext:junit:${Versions.Test.junitVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Test.espressoCore}"
    }

    object Core {
        const val coreKtx = "androidx.core:core-ktx:${Versions.Core.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.Core.appcompat}"
        const val material = "com.google.android.material:material:${Versions.Core.material}"
        const val activity = "androidx.activity:activity:${Versions.Core.activity}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.Core.constraintlayout}"
    }
}