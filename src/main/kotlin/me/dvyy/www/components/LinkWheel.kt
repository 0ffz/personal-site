package me.dvyy.www.components

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.div


fun FlowContent.linkWheel(pageName: String) = div("flex overflow-x-auto space-x-4 items-center") {
    mapOf(
        "Home" to "/",
        "Projects" to "/projects",
        "Blog" to "/blog",
        "GitHub" to "https://github.com/0ffz",
        "Contact" to "mailto:contact@dvyy.me"
    ).forEach { (name, link) ->
        a(href = link, classes = if (name == pageName) "!text-purple-50" else "") { +name }
    }
}
