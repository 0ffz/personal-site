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
    postInformation(bleed = true, this@blogPost)
    markdown(content)
}

fun FlowContent.postInformation(bleed: Boolean, page: Page) {
    page.desc?.let {
        div("my-2") {
            secondaryText(it)
        }
    }
    chipList(bleed) {
        page.formattedDate?.let {
            div { icons.calendar }
            secondaryText(it)
        }

        if (page.tags.isNotEmpty()) {
            div("pl-2") { icons.tags }
            for (tag in page.tags) outlinedChip(tag)
        }
    }
}

fun FlowContent.chipList(bleed: Boolean = false, content: DIV.() -> Unit) {
    val bleedClasses = if(bleed) "max-md:px-[20px] max-md:full-bleed" else ""
    div("$bleedClasses my-1 flex overflow-x-auto space-x-1 items-center content-start scrollbar-hide") {
        content()
    }
}
