plugins {
    `build-scan`
    kotlin("jvm") version "1.3.41"
    id("org.jetbrains.dokka") version "0.9.18"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")
    testImplementation("junit:junit:4.12")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/docs"
}
