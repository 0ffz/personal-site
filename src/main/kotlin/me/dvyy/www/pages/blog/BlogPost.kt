package me.dvyy.www.pages.blog

import kotlinx.html.HTML
import kotlinx.serialization.Serializable
import me.dvyy.www.generation.Page
import me.dvyy.www.generation.markdown
import me.dvyy.www.templates.defaultTemplate

@Serializable
data class BlogPost(
    val year: String? = null,
)

fun HTML.blogPost(page: Page<BlogPost>) = defaultTemplate(page.title, smallPage = false, desc = buildString {
    append(page.dateAndDesc)
}) {
    markdown(page.content)
}
