package me.dvyy.www.components

import kotlinx.html.FlowContent
import kotlinx.html.a

fun FlowContent.pageLink(href: String,  text: String, classes: String = "") =
    a(href = href, classes = "text-purple-300 no-underline hover:text-purple-200 $classes") { +text }
