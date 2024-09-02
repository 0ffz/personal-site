import kotlinx.html.HTML
import me.dvyy.www.sitegen.Pages
import me.dvyy.www.sitegen.siteRoot
import templates.blog.BlogPost
import templates.blog.blogIndex
import templates.blog.blogPost
import templates.home
import templates.projects
import kotlin.io.path.Path


val routing = siteRoot {
    "index" shows HTML::home
    "projects" shows HTML::projects
    "blog" {
        "index" shows HTML::blogIndex
        Pages.forPath<BlogPost>(Path("site/blog")).forEach { ref ->
            ref.url shows {
                val page = ref.read()
                it.blogPost(page)
            }
        }
    }
}
