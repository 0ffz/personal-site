import me.dvyy.shocky.Shocky
import me.dvyy.shocky.page.CommonFrontMatter
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.siteRouting
import templates.blog.blogIndex
import templates.blog.blogPost
import templates.defaultTemplate
import kotlin.io.path.Path

suspend fun main(args: Array<String>) = Shocky(
    dest = Path("out"),
    port = 4000,
    route = siteRouting(path = Path("site")) {
        template("default", Page::defaultTemplate)
        template("blog", Page::blogPost)

        includeAssets()
        pages(".")

        "blog" {
            generate(meta = CommonFrontMatter(title = "Blog")) { blogIndex() }
        }
    },
    assets = listOf(Path("site/assets")),
    watch = listOf(Path("site")),
    useTailwind = true
).run(args)
