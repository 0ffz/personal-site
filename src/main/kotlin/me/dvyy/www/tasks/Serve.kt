package me.dvyy.www.tasks

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.dvyy.www.startServer

suspend fun main(args: Array<String>): Unit = coroutineScope {
    launch {
        startServer(args)
    }

    launch {
        ProcessBuilder("./gradlew", "-t", "build").apply {
            environment()["JAVA_HOME"] = System.getProperty("java.home")
            redirectInput(ProcessBuilder.Redirect.INHERIT)
//            redirectOutput(ProcessBuilder.Redirect.INHERIT)
            redirectError(ProcessBuilder.Redirect.INHERIT)
        }.start().inputStream.bufferedReader().lineSequence().forEach {
            val line = it.lowercase()
            if(listOf("rebuilding", "build").any { line.startsWith(it) }) {
                println(it)
            }
        }
    }
}
