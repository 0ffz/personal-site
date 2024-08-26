package me.dvyy.www

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import me.dvyy.www.generation.SiteRouteDSL
import me.dvyy.www.generation.routesToServer

internal val logger = KtorSimpleLogger("me.dvyy.Website")

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    routing {
        routesToServer(routing)
        staticResources("/static", "assets")
    }
}

