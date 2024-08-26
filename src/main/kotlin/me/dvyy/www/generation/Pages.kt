package me.dvyy.www.generation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import java.nio.file.Path
import kotlin.io.path.*

data class PageReference<T>(
    val frontMatterSerializer: KSerializer<T>,
    val url: String,
    val path: Path,
) {
    fun read(): Page<T> {
        return Page.fromFile(frontMatterSerializer, path)
    }
}

object Pages {
    inline fun <reified T> forPath(path: Path): List<PageReference<T>> {
        if (!path.exists()) return emptyList()
        val serializer = serializer<T>()
        return path
            .listDirectoryEntries()
            .filter { it.isRegularFile() }
            .map {
                PageReference(serializer, it.relativeTo(path).toString().removeSuffix(".md"), it)
            }
    }
}
