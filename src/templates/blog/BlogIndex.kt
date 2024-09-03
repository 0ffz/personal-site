package templates.blog

import kotlinx.html.*
import me.dvyy.www.sitegen.page.Pages
import me.dvyy.www.sitegen.page.Page
import templates.defaultTemplate
import kotlin.io.path.Path

fun Page.blogIndex() = defaultTemplate {
    val posts = Pages
        .walk(Path("site/blog"), Path("site"))
        .map { it.readFrontMatter() }

    posts.groupBy { it.meta<BlogPost>().year ?: "Unknown year" }
        .toSortedMap { a, b -> b.compareTo(a) }
        .forEach { (year, posts) ->
            h2 { +year }
            ul {
                posts.sortedByDescending { it.date }.forEach { post ->
                    li {
                        div("flex space-x-2 items-center text-zinc-400") {
                            a(href = post.url) { +post.title }
                            span { i { +(post.desc ?: "") } }
                        }
                    }
                }
            }
        }
}
