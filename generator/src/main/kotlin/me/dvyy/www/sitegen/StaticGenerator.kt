package me.dvyy.www.sitegen

import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.write
import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import java.nio.file.Path
import kotlin.io.path.*
import kotlin.time.measureTime

private fun runCommand(vararg args: String) {
    ProcessBuilder(*args).apply {
        inheritIO()
    }.start().waitFor()
}

class StaticGenerator(
    val dest: Path,
    val root: SiteRouting,
    val extraInputs: List<Path>,
    val devMode: Boolean,
) {
    @OptIn(ExperimentalPathApi::class)
    fun generate() {
        measureTime {
            if (!devMode) dest.deleteRecursively()
            dest.createDirectories()
        }.let { println("Cleared output in: $it") }
        runCommand("npx", "tailwindcss", "-o", "out/assets/tailwind/styles.css", "--minify")
        measureTime {
            extraInputs
                .forEach { it.copyToRecursively(dest / it.name, followLinks = false, overwrite = true) }
        }.let { println("Copied extra inputs in: $it") }
        measureTime { generateDocuments() }.let { println("Generated html files in: $it") }
    }

    fun generateDocuments() {
        root.assets.forEach {
            val dest = dest / it.relativeTo(root.route)
            dest.createParentDirectories()
            it.copyTo(dest, overwrite = true)
        }
        root.documents.forEach { document ->
            val path = dest / document.path.relativeTo(root.route)
            path.createParentDirectories().also { if (it.notExists()) it.createFile() }
                .writer()
                .use { writer ->
                    document.page.html?.let { writer.write(it) }
                }
        }
    }
}
