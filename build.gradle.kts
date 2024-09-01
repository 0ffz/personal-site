plugins {
    alias(libs.plugins.kotlinx.serialization)
    kotlin("jvm") version "2.0.20"
}

group = "me.dvyy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.html)
    implementation(libs.markdown)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks{
    test {
        useJUnitPlatform()
    }

    register("tailwind") {
        exec {
            commandLine("npx", "tailwindcss", "-o", "src/main/resources/assets/tailwind/styles.css", "--minify")
        }
    }

    assemble {
        dependsOn("tailwind")
    }

    register<JavaExec>("generate") {
        dependsOn(assemble)
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("me.dvyy.www.generation.StaticGeneratorKt")
    }
}
kotlin {
    jvmToolchain(17)
}

//application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
//}
