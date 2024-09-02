package me.dvyy.www.sitegen

import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.time.measureTime

fun runCommand(vararg args: String) {
    ProcessBuilder(*args).apply {
        inheritIO()
    }.start().waitFor()
}

class StaticGenerator(
    val output: Path,
    val routing: SiteRoute,
    val extraInputs: List<Path>,
    val devMode: Boolean,
) {
    @OptIn(ExperimentalPathApi::class)
    fun generate() {
        measureTime {
            if (!devMode) output.deleteRecursively()
            output.createDirectories()
        }.let { println("Cleared output in: $it") }
        runCommand("npx", "tailwindcss", "-o", "out/assets/tailwind/styles.css", "--minify")
        measureTime {
            extraInputs
                .forEach { it.copyToRecursively(output / it.name, followLinks = false, overwrite = true) }
        }.let { println("Copied extra inputs in: $it") }
        measureTime { generate(output, routing) }.let { println("Generated html files in: $it") }
    }

    fun generate(root: Path, route: SiteRoute): Unit = when (route) {
        is SiteRoute.Route -> {
            val subroute = if (route.name == "/") root else root / route.name
            route.subRoutes.forEach { generate(subroute, it) }
        }

        is SiteRoute.Document -> {
            val path = root / "${route.name}.html"
            path.createParentDirectories().also { if (it.notExists()) it.createFile() }.writeText(buildString {
                appendHTML().html {
                    route.html(this)
                }
            })
        }
    }
}
