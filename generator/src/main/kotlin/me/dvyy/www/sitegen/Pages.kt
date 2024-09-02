package me.dvyy.www.sitegen

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.*

data class PageReference<T>(
    val frontMatterSerializer: KSerializer<T>,
    val url: String,
    val path: Path,
    val fileName: String,
) {
    fun read(): Page<T> {
        return Page.fromFile(frontMatterSerializer, path)
    }
}

object Pages {
    @OptIn(ExperimentalPathApi::class)
    inline fun <reified T> forPath(path: Path): List<PageReference<T>> {
        if (!path.exists()) return emptyList()
        val serializer = serializer<T>()
        return path
            .walk()
            .filter { it.isRegularFile() }
            .map {
                PageReference(serializer, it.relativeTo(path).toString().removeSuffix(".md"), it, it.nameWithoutExtension)
            }
            .toList()
    }
}
