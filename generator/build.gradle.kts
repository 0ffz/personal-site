plugins {
    alias(libs.plugins.kotlinx.serialization)
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.markdown)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.kaml)
    implementation(libs.kotlinx.serialization.json)
    implementation("ch.qos.logback:logback-classic:1.5.6")

    api(libs.kotlinx.datetime)
    api(libs.kotlinx.html)
}
