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
     * Depedency Injection
     */
    implementation(platform(Dependencies.Koin.bom))
    implementation(Dependencies.Koin.core)

    /**
     * Test
     */
    testImplementation(Dependencies.Test.koin)
}