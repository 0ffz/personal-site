package templates

import kotlinx.html.*
import me.dvyy.www.sitegen.markdown
import kotlin.io.path.Path
import kotlin.io.path.readText

fun HTML.home() = defaultTemplate("Home", title = "Danielle Voznyy", smallPage = true) {
    markdown(Path("site/index.md").readText())
}
