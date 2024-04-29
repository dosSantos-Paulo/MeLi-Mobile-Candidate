plugins {
    id(Plugins.javaLib)
    id(Plugins.kotlinJvm)
    id(Plugins.detekt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    /**
     * Modules
     */
    implementation(project(Modules.domain))

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
}