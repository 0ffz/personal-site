package me.dvyy.www.pages

import kotlinx.html.*
import me.dvyy.www.generation.markdown
import me.dvyy.www.templates.defaultTemplate
import kotlin.io.path.Path
import kotlin.io.path.readText

fun HTML.home() = defaultTemplate("Home", title = "Danielle Voznyy", smallPage = true) {
    markdown(Path("pages/index.md").readText())
}
