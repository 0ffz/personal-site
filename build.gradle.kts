plugins {
    alias(libs.plugins.kotlinx.serialization)
    kotlin("jvm") version "2.0.20"
}

group = "me.dvyy"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(project(":generator"))
    implementation(libs.kotlinx.html)
    implementation(libs.kotlinx.serialization.json)
}

sourceSets.main {
    kotlin.srcDirs("src")
    resources.srcDirs("site")
}

tasks{
    test {
        useJUnitPlatform()
    }

//    register("tailwind") {
//        exec {
//            commandLine("npx", "tailwindcss", "-o", "out/assets/tailwind/styles.css", "--minify")
//        }
//    }
//
//    assemble {
//        dependsOn("tailwind")
//    }
    build {
        dependsOn(":generator:build")
    }

    register<JavaExec>("generate") {
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("GenerateKt")
    }
}

kotlin {
    jvmToolchain(17)
}
