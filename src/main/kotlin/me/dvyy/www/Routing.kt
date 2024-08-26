package me.dvyy.www

import kotlinx.html.HTML
import me.dvyy.www.generation.Page
import me.dvyy.www.generation.SiteRouteDSL
import me.dvyy.www.generation.siteRoot
import me.dvyy.www.pages.*
import me.dvyy.www.pages.blog.BlogPost
import me.dvyy.www.pages.blog.blogIndex
import me.dvyy.www.pages.blog.blogPost
import kotlin.io.path.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo


val routing = siteRoot {
    "index" shows HTML::home
    "projects" shows HTML::projects
    "blog" {
        "index" shows HTML::blogIndex
        Path("pages/blog")
            .listDirectoryEntries()
            .filter { it.isRegularFile() }
            .forEach { path ->
                val url = path.relativeTo(Path("pages/blog")).toString().removeSuffix(".md")
                url shows {
                    val post = Page.fromFile<BlogPost>(path)
                    it.blogPost(post.frontMatter, post.content)
                }
            }
    }
}
