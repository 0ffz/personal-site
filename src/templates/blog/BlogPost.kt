package templates.blog

import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.serialization.Serializable
import components.outlinedChip
import me.dvyy.www.sitegen.Page
import me.dvyy.www.sitegen.markdown
import templates.defaultTemplate

@Serializable
data class BlogPost(
    val year: String? = null,
    val tags: List<String> = listOf(),
)

fun HTML.blogPost(page: Page<BlogPost>) = defaultTemplate(page.title, smallPage = false, desc = buildString {
    append(page.dateAndDesc)
}) {
    div("flex overflow-x-auto space-x-1 items-center") {
        for (tag in page.meta.tags) {
            outlinedChip(tag)
        }
    }
    markdown(page.content)
}
