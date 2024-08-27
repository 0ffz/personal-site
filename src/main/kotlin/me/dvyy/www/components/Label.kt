package me.dvyy.www.components

import kotlinx.html.FlowContent
import kotlinx.html.div

fun FlowContent.outlinedChip(name: String) {
    div("border-2 text-nowrap border-zinc-700 text-zinc-300 text-xs font-semibold uppercase py-1 px-2 rounded-full") {
        +name
    }
}
