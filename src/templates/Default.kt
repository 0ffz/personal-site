package templates

import kotlinx.html.*
import components.linkWheel
import kotlinx.serialization.Serializable
import me.dvyy.shocky.markdown
import me.dvyy.shocky.page.Page

@Serializable
data class DefaultMeta(
    val smallPage: Boolean = false,
    val showFooter: Boolean = true,
)

inline fun Page.defaultTemplate(
    syntaxHighlighting: Boolean = false,
    crossinline init: FlowContent.() -> Unit = { markdown(content) },
) = html {
    val meta = meta<DefaultMeta>()
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = LinkRel.stylesheet, href = "/assets/tailwind/styles.css")
//        link(rel = LinkRel.stylesheet, href = "/assets/custom.css")
        if(syntaxHighlighting) {
            script(src = "/assets/scripts/prism.js") {}
            link(rel = LinkRel.stylesheet, href = "/assets/scripts/prism.css")
            link(rel = LinkRel.stylesheet, href = "/assets/scripts/theme-darcula.css")
        }
        title(when(val title = page.title) {
            "Projects", "Blog" -> "Danielle's $title"
            else -> title
        })
    }
    body(classes = "bg-zinc-100 dark:bg-zinc-900 min-h-screen overflow-x-hidden") {
        div(
            """
                prose prose-zinc dark:prose-invert max-w-none
                prose-a:text-purple-500 prose-a:hover:text-purple-400
                dark:prose-a:text-purple-300 dark:prose-a:hover:text-purple-200
                prose-a:no-underline
                prose-img:mx-auto prose-img:my-3
                h-auto pt-4 md:pt-8 lg:pt-20 xl:pt-36
                prose-figcaption:mx-auto prose-figcaption:italic prose-figcaption:mt-0 prose-figcaption:mb-2 prose-figcaption:px-4
                wrapper
                max-md:prose-img:full-bleed
                prose-p:mt-3 prose-p:mb-1 prose-ul:my-3
                prose-li:my-1
                prose-h1:mb-2 prose-h2:mb-1 prose-h3:mb-1 prose-h4:mb-1
                prose-h1:mt-10 prose-h2:mt-8 prose-h3:mt-6 prose-h4:mt-6
                !prose-pre:overflow-hidden
                !prose-code:overflow-hidden
                prose-code:!font-mono prose-code:!break-word
                prose-code:before:content-none prose-code:after:content-none
                prose-pre:rounded-none prose-pre:full-bleed prose-pre:wrapper prose-pre:!px-0
                """.trimIndent()
        ) {
            smallWrap(meta.smallPage) {
                h1("mb-2") { +page.title }
                linkWheel(page.title)
//                if (page.desc != null) div("pt-2") {
//                    p("text-zinc-400 m-0") { i { +page.desc!! } }
//                }
                init()
                if (meta.showFooter) {
                    div("md:h-36")
                }
            }
        }
    }
}

inline fun FlowContent.smallWrap(smallPage: Boolean, crossinline init: FlowContent.() -> Unit) {
    if(smallPage) div("max-w-xl") {
        init()
    } else init()
}
