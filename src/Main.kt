import me.dvyy.shocky.page.CommonFrontMatter
import me.dvyy.shocky.page.Page
import me.dvyy.shocky.shocky
import templates.blog.blogIndex
import templates.blog.blogPost
import templates.defaultTemplate
import pages.projectsPage
import kotlin.io.path.div

suspend fun main(args: Array<String>) = shocky {
    dest("out")
    assets("site/assets")
    watch("site")
    siteRoot("site")
    tailwind {
        inputCss = siteRoot / "custom.css"
    }
    routing {
        template("default", Page::defaultTemplate)
        template("blog", Page::blogPost)

        includeAssets()
        pages(".")

        generate("projects", meta = CommonFrontMatter(title = "Projects")) {
            projectsPage()
        }
        "blog" {
            generate(meta = CommonFrontMatter(title = "Blog")) { blogIndex() }
        }
    }
}.run(args)
