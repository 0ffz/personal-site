package me.dvyy.www.sitegen

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

fun startServer(
    configure: Application.() -> Unit = {},
) {
//    System.setProperty("io.ktor.development", "true")
    embeddedServer(
        Netty,
        port = 8080,
        host = "localhost",
    ) {
        routing {
            staticFiles("/", File("out")) {
                extensions("html")
            }
        }
        configure(this)
    }.start(wait = true)
}


suspend fun startServerAndWatch(): Unit = coroutineScope {
    launch {
        startServer()
    }

    launch {
        ProcessBuilder("./gradlew", "-t", "generate").apply {
            environment()["JAVA_HOME"] = System.getProperty("java.home")
            environment()["DEVELOPMENT"] = "true"
            redirectInput(ProcessBuilder.Redirect.INHERIT)
            redirectOutput(ProcessBuilder.Redirect.INHERIT)
            redirectError(ProcessBuilder.Redirect.INHERIT)
        }.start()/*.inputStream.bufferedReader().lineSequence().forEach {
            val line = it.lowercase()
            if(listOf("rebuilding", "build").any { line.startsWith(it) }) {
                println(it)
            }
        }*/
    }
}
