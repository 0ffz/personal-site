package templates.blog

import components.outlinedChip
import components.secondaryText
import icons
import kotlinx.html.div
import kotlinx.serialization.Serializable
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.markdown
import templates.defaultTemplate

@Serializable
data class BlogPost(
    val year: String? = null,
)


fun Page.blogPost() = defaultTemplate(syntaxHighlighting = true) {
    div("my-2 flex overflow-x-auto space-x-1 items-center content-start scrollbar-hide") {
        formattedDate?.let {
            icons.calendar
            secondaryText(it)
        }
        desc?.let {
            div("pl-2") { icons.info }
            secondaryText(it)
        }

        if (tags.isNotEmpty()) {
            div("pl-2") { icons.tags }
            for (tag in tags) outlinedChip(tag)
        }
    }
    markdown(content)
}
