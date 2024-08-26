package me.dvyy.www.generation

import io.ktor.server.http.content.*
import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import me.dvyy.www.routing
import java.nio.file.Path
import kotlin.io.path.*

class StaticGenerator(
    val root: Path,
    val routing: SiteRoute,
    val static: Path,
) {
    @OptIn(ExperimentalPathApi::class)
    fun generate() {
        root.deleteRecursively()
        val staticRoot = (root / "static").createParentDirectories()
        static.copyToRecursively(staticRoot, followLinks = false)
        generate(root, routing)
    }

    fun generate(root: Path, route: SiteRoute): Unit = when (route) {
        is SiteRoute.Route -> {
            val subroute = if (route.name == "/") root else root / route.name
            route.subRoutes.forEach { generate(subroute, it) }
        }

        is SiteRoute.Document -> {
            val path = root / "${route.name}.html"
            path.createParentDirectories().createFile().writeText(buildString {
                appendHTML().html {
                    route.html(this)
                }
            })
        }
    }
}

fun main() {
    val root = Path("out")
    StaticGenerator(root, routing, Path("src/main/resources/assets")).generate()
}
