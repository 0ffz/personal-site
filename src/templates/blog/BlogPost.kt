package templates.blog

import components.outlinedChip
import components.secondaryText
import icons
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page
import templates.defaultTemplate

@Serializable
data class BlogPost(
    val year: String? = null,
)


fun Page.blogPost() = defaultTemplate(syntaxHighlighting = true) {
    postInformation(this@blogPost)
    markdown(content)
}

fun FlowContent.postInformation(page: Page) {
    div("flex-col") {
        page.desc?.let {
            div("my-2") {
                secondaryText(it)
            }
        }
        div("my-2 flex overflow-x-auto space-x-1 items-center content-start scrollbar-hide") {
            page.formattedDate?.let {
                icons.calendar
                secondaryText(it)
            }

            if (page.tags.isNotEmpty()) {
                div("pl-2") { icons.tags }
                for (tag in page.tags) outlinedChip(tag)
            }
        }
    }
}
