package me.dvyy.www

import kotlinx.html.HTML
import me.dvyy.www.generation.Pages
import me.dvyy.www.generation.siteRoot
import me.dvyy.www.pages.blog.BlogPost
import me.dvyy.www.pages.blog.blogIndex
import me.dvyy.www.pages.blog.blogPost
import me.dvyy.www.pages.home
import me.dvyy.www.pages.projects
import kotlin.io.path.Path


val routing = siteRoot {
    "index" shows HTML::home
    "projects" shows HTML::projects
    "blog" {
        "index" shows HTML::blogIndex
        Pages.forPath<BlogPost>(Path("pages/blog")).forEach { ref ->
            ref.url shows {
                val post = ref.read()
                it.blogPost(post.frontMatter, post.content)
            }
        }
    }
}
