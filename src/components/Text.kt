package components

import kotlinx.html.FlowContent
import kotlinx.html.i
import kotlinx.html.p

fun FlowContent.secondaryText(text: String, classes: String = "") {
    p("text-zinc-800 dark:text-zinc-400 !my-0 $classes flex-shrink-0") { i { +text } }
}
