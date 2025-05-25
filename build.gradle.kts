plugins {
    application
    kotlin("jvm") version "2.2.0-RC"
    kotlin("plugin.serialization") version "2.2.0-RC"
}


repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    implementation("me.dvyy:shocky:0.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

sourceSets {
    main {
        kotlin.srcDirs("src")
    }
}

application {
    mainClass = "MainKt"
}

tasks {
    register("generate") {
        run.get().args("generate")
        finalizedBy(run)
    }
    register("serve") {
        run.get().args("serve")
        finalizedBy(run)
    }
}
