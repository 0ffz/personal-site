package me.dvyy.www.sitegen

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlMap
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.relativeTo
import kotlin.io.path.useLines

data class Page<T>(
    val meta: T,
    val content: String,
    val title: String,
    val desc: String?,
    val url: String,
    val date: LocalDate? = null,
) {
    val formattedDate get() = date?.format(dateFormat)
    val dateAndDesc get() = buildString {
        if (date != null) {
            append(formattedDate)
            if (desc != null) append(" - ")
        }
        append(desc ?: "")
    }

    companion object {
        val dateFormat = LocalDate.Format { monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); dayOfMonth(); chars(", "); year(); }
        val yaml = Yaml { }

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

                val props = yaml.decodeYamlFromString(frontMatter.ifEmpty { "{}" }) as? YamlMap
                val title = props?.getStringOrNull("title") ?: path.nameWithoutExtension
                val url = path.relativeTo(Path("pages")).toString().removeSuffix(".md")
                val desc = props?.getStringOrNull("desc")
                val date = props?.getStringOrNull("date")?.let { LocalDate.parse(it) }

                return when {
                    frontMatter.isEmpty() && default != null -> Page(default, content, title, desc, url, date)
                    else -> Page(yaml.decodeFromString(serializer, frontMatter), content, title, desc, url, date)
                }
            }
        }

    }
}
