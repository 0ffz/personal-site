package me.dvyy.www.sitegen

import kotlinx.serialization.serializer
import me.dvyy.www.sitegen.page.Page
import me.dvyy.www.sitegen.page.PageReference.Companion.yaml
import me.dvyy.www.sitegen.page.Pages
import java.nio.file.Path
import kotlin.io.path.*

class SiteRouting(
    rootOrNull: SiteRouting?,
    val route: Path = Path("site"),
    val url: String = "",
) {
    val routingRoot = rootOrNull ?: this
    val assets = mutableListOf<Path>()
    val documents = mutableListOf<Document>()

    operator fun String.invoke(block: SiteRouting.() -> Unit) {
        SiteRouting(routingRoot, route / this, "$url/$this").apply(block)
    }

    inline fun <reified T> generate(
        path: String = "index",
        meta: T,
        content: String = "",
        html: Page.() -> Unit,
    ) {
        val frontMatter = yaml.encodeToString(serializer<T>(), meta)
        val outputPath = Pages.outputFor(route / "$path.html")
        val page = Pages.generate(route / path, route, frontMatter, content)
        page.apply(html)
        addDocument(Document(outputPath, page))
    }

    inline fun pages(
        path: String = ".",
        init: Page.() -> Unit,
    ) = Pages.walk(route / path, routingRoot.route).forEach { page ->
        addDocument(Document(page.outputPath, page.read().apply(init)))
    }

    fun page(path: String, init: Page.() -> Unit) {
        val ref = Pages.single(route / path, relativeTo = routingRoot.route / url)
        addDocument(Document(ref.outputPath, ref.read().apply(init)))
    }

    @OptIn(ExperimentalPathApi::class)
    fun includeAssets(path: String = ".", extensions: List<String> = listOf("png", "jpg", "jpeg", "avif")) {
        val paths = (route / path).walk().filter { it.extension in extensions }
        withRoot {
            paths.forEach { assets.add(it) }
        }
    }

    fun addDocument(document: Document) = withRoot {
        documents.add(document)
    }

    private inline fun withRoot(run: SiteRouting.() -> Unit) = if (routingRoot == null) run() else routingRoot.run()

}

class Document(val path: Path, val page: Page)

inline fun siteRouting(
    path: Path = Path("site"),
    rootUrl: String = "",
    crossinline block: SiteRouting.() -> Unit,
) = SiteRouting(null, path, rootUrl).apply(block)

