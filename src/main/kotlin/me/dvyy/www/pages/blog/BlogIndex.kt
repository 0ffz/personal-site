package me.dvyy.www.pages.blog

import kotlinx.html.*
import me.dvyy.www.generation.Page
import me.dvyy.www.generation.Pages
import me.dvyy.www.templates.defaultTemplate
import kotlin.io.path.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo

fun HTML.blogIndex() = defaultTemplate("Blog", smallPage = false) {
    val blogRoot = Path("pages/blog")
    val posts = Pages.forPath<BlogPost>(blogRoot).map { it.read().frontMatter.copy(url = "blog/${it.url}") }

    posts.groupBy { it.year }.forEach { (year, posts) ->
        h2 { +(year ?: "No year") }
        ul {
            posts.forEach { post ->
                li {
                    div("flex space-x-2 items-center") {
                        a(href = post.url) { +post.title }
                        if(post.desc != null)
                            div("text-zinc-400") { i { +"(${post.desc.take(100)})" } }
                    }
                }
            }
        }
    }
}
