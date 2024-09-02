package me.dvyy.www

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import me.dvyy.www.generation.routesToServer
import java.io.File

internal val logger = KtorSimpleLogger("me.dvyy.Website")

fun startServer(args: Array<String>) = main(args)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    routing {
        routesToServer(routing)
        staticFiles("/assets", File("assets"))
    }
}

