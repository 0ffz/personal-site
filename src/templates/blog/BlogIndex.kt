package templates.blog

import kotlinx.html.*
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.page.Pages
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
                    li("pb-2") {
                        div("flex flex-col dark:text-zinc-400") {
                            a(href = post.url) { +post.title }
                            postInformation(bleed = false, post)
//                            span { i { +(post.desc ?: "") } }
//                            div("flex") {
//                                post.tags.forEach { outlinedChip(it) }
//                            }
                        }
                    }
                }
            }
        }
}
