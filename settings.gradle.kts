rootProject.name = "personal-site-kotlin"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases")
        mavenLocal()
    }
}

dependencyResolutionManagement {
    val shockyVersion: String by settings

    repositories {
        maven("https://repo.mineinabyss.com/releases")
        mavenLocal()
    }
    versionCatalogs {
        create("shockyLibs").from("me.dvyy:catalog:$shockyVersion")
    }
}
