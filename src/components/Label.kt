package components

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div

fun FlowContent.outlinedChip(name: String, url: String? = null) {
    val color = if(url == null) "border-zinc-300 dark:border-zinc-700" else "border-purple-300 dark:border-purple-300"
    div("border-2 text-nowrap $color text-zinc-700 dark:text-zinc-300 text-xs font-semibold uppercase py-1 px-2 rounded-full") {
        if(url != null) a(href = url) { +name }
        else +name
    }
}
