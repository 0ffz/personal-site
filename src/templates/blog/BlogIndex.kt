package templates.blog

import kotlinx.html.*
import me.dvyy.www.sitegen.Pages
import templates.defaultTemplate
import kotlin.io.path.Path

fun HTML.blogIndex() = defaultTemplate("Blog", smallPage = false) {
    val blogRoot = Path("templates/blogblog")
    val posts = Pages
        .forPath<BlogPost>(blogRoot)
        .map { it.read() }

    posts.groupBy { it.meta.year ?: "Unknown year" }
        .toSortedMap { a, b -> b.compareTo(a) }
        .forEach { (year, posts) ->
            h2 { +year }
            ul {
                posts.sortedByDescending { it.date }.forEach { post ->
                    li {
                        div("flex space-x-2 items-center text-zinc-400") {
                            a(href = post.url) { +post.title }
                            span { i { +post.dateAndDesc } }
                        }
                    }
                }
            }
        }
}