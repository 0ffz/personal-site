package me.dvyy.www.generation

import io.ktor.server.html.*
import io.ktor.server.routing.*

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
