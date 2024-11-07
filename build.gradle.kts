plugins {
    alias(shockyLibs.plugins.shocky)
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

dependencies {
    implementation(shockyLibs.bundles.shocky)
}

sourceSets.main {
    kotlin.srcDirs("src")
    resources.srcDirs("site")
}
