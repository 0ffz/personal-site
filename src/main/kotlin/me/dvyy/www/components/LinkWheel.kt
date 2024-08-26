package me.dvyy.www.components

import kotlinx.html.FlowContent
import kotlinx.html.div


fun FlowContent.linkWheel(pageName: String) = div("flex overflow-x-auto space-x-4 items-center") {
    mapOf(
        "Home" to "/",
        "Projects" to "/projects",
    ).forEach { (name, link) ->
        pageLink(link, name, classes = if (name == pageName) "!text-purple-50" else "")
    }
}
