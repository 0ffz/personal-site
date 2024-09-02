package templates

import kotlinx.html.HTML
import me.dvyy.www.sitegen.markdown
import kotlin.io.path.Path
import kotlin.io.path.readText

fun HTML.projects() = defaultTemplate("Projects", smallPage = false) {
    markdown(Path("site/projects.md").readText())
}
