package me.dvyy.www.pages.blog

import kotlinx.html.HTML
import kotlinx.serialization.Serializable
import me.dvyy.www.generation.markdown
import me.dvyy.www.templates.defaultTemplate

@Serializable
data class BlogPost(
    val year: String? = null,
    val title: String = "Untitled",
    val desc: String? = null,
    val url: String? = null,
)

fun HTML.blogPost(post: BlogPost, content: String) = defaultTemplate(post.title, smallPage = false) {
    markdown(content)
}
