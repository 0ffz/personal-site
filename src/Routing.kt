import me.dvyy.www.sitegen.page.CommonFrontMatter
import me.dvyy.www.sitegen.siteRouting
import templates.blog.blogIndex
import templates.blog.blogPost
import templates.defaultTemplate
import kotlin.io.path.Path


val routing = siteRouting(
    path = Path("site")
) {
    page("index.md") { defaultTemplate() }
    page("projects.md") { defaultTemplate() }
    documents
    "blog" {
        generate(meta = CommonFrontMatter(title = "Blog")) { blogIndex() }
        includeAssets()
        pages { blogPost() }
    }
}
