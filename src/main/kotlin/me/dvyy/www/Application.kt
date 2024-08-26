package me.dvyy.www

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.html.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

internal val logger = KtorSimpleLogger("me.dvyy.Website")

sealed interface SiteRoute {
    val name: String

    data class Route(override val name: String, val subRoutes: List<SiteRoute>) : SiteRoute
    data class Document(override val name: String, val html: (HTML) -> Unit) : SiteRoute
}

class SiteRouteDSL(val name: String) {
    val subRoutes: MutableList<SiteRoute> = mutableListOf()
    operator fun String.invoke(block: SiteRouteDSL.() -> Unit) {
        subRoutes.add(SiteRouteDSL(this).apply(block).build())
    }

    infix fun String.shows(html: Function1<HTML, Unit>) {
        subRoutes.add(SiteRoute.Document(this, html))
    }
//    infix fun String.shows(html: HtmlBuilder) {
//        subRoutes.add(SiteRoute.Document(this, html))
//    }

    fun build() = SiteRoute.Route(name, subRoutes)
}

//interface HtmlBuilder {
//    fun applyTo(input: HTML)
//}


val flavour = CommonMarkFlavourDescriptor()

fun FlowContent.markdown(src: String) {
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
    val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()
    (this as? HTMLTag)?.unsafe {
        +html
    }
}

fun HTMLTag.markdown(src: String) {
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
    val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()
    unsafe {
        +html
    }
}

inline fun siteRoot(crossinline block: SiteRouteDSL.() -> Unit) = SiteRouteDSL("/").apply(block).build()

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
//    val out = Path("out")
//
//    logger.info("Server started at http://localhost:8080")
//    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
//    }.start(wait = true)
    routing {
        routesToServer(routing)
    }
}

fun Routing.routesToServer(routing: SiteRoute) {
    when (routing) {
        is SiteRoute.Route -> {
            val index = routing.subRoutes.firstOrNull { it.name == "index" } as? SiteRoute.Document
            route(routing.name) {
                if (index != null) {
                    get {
                        call.respondHtml {
                            index.html.invoke(this)
                        }
                    }
                }

                routing.subRoutes.forEach { subRoute ->
                    routesToServer(subRoute)
                }
            }
        }

        is SiteRoute.Document -> {
            get(routing.name) {
                call.respondHtml {
                    routing.html.invoke(this)
                }
            }
        }
    }
}
