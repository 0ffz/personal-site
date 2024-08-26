package me.dvyy.www.pages

import kotlinx.html.*
import me.dvyy.www.markdown
import me.dvyy.www.templates.defaultTemplate
import kotlin.io.path.Path
import kotlin.io.path.readText

//fun resource(path: String): String? = SiteRoute::class.java.getResource(path)?.readText()

fun HTML.home() = defaultTemplate("Home", smallPage = true) {
    markdown(Path("pages/index.md").readText())
}

fun FlowContent.button(number: Int) = button {
    +"Click me $number!"
}
