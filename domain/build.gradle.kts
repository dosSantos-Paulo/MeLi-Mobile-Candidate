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
     * Coroutines
     */
    implementation(Dependencies.Coroutines.core)

    /**
     * Test
     */
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockK)
}