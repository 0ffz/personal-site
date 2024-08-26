package me.dvyy.www.generation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import net.mamoe.yamlkt.Yaml
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.useLines

data class Page<T>(
    val frontMatter: T,
    val content: String,
) {

    companion object {
        val yaml = Yaml { }
        fun fromFile(path: Path): Page<Any?> = Page(null, path.readText())

        inline fun <reified T> fromFile(path: Path, default: T? = null): Page<T> =
            fromFile(serializer<T>(), path, default)

        fun <T> fromFile(serializer: KSerializer<T>, path: Path, default: T? = null): Page<T> {
            path.useLines {
                var frontMatter = ""
                val content = it.fold(StringBuilder()) { acc, line ->
                    if (line == "---") {
                        if (frontMatter.isEmpty()) {
                            frontMatter = acc.toString()
                            acc.clear()
                        }
                        acc
                    } else {
                        acc.appendLine(line)
                    }
                }.toString()
                return when {
                    frontMatter.isEmpty() && default != null -> Page(default, content)
                    else -> Page(yaml.decodeFromString(serializer, frontMatter), content)
                }
            }
        }

    }
}
