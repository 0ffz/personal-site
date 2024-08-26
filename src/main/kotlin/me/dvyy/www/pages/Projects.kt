package me.dvyy.www.pages

import kotlinx.html.HTML
import me.dvyy.www.generation.markdown
import me.dvyy.www.templates.defaultTemplate
import kotlin.io.path.Path
import kotlin.io.path.readText

fun HTML.projects() = defaultTemplate("Projects", smallPage = false) {
    markdown(Path("pages/projects.md").readText())
}
