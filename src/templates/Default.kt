package templates

import kotlinx.html.*
import components.linkWheel

inline fun HTML.defaultTemplate(
    pageName: String,
    title: String = pageName,
    smallPage: Boolean = false,
    showFooter: Boolean = true,
    desc: String? = null,
    crossinline init: FlowContent.() -> Unit,
) {
    head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = "stylesheet", href = "/assets/tailwind/styles.css")
        link(rel = "stylesheet", href = "/assets/custom.css")
        title(title)
    }
    body(classes = "bg-zinc-900 min-h-screen overflow-x-hidden") {
        div(
            """
                prose prose-zinc dark:prose-invert max-w-none
                prose-a:text-purple-300 prose-a:no-underline hover:prose-a:text-purple-200
                prose-h2:mb-2 prose-h3:mb-2 prose-h4:mb-1
                prose-h2:mt-4 prose-h3:mt-4 prose-h4:mt-2
                prose-img:mx-auto prose-img:my-4
                prose-figcaption:mx-auto prose-figcaption:italic prose-figcaption:mt-0
                wrapper
                prose-ul:my-0 prose-p:mt-0 prose-p:mb-4
                h-auto pt-4 md:pt-8 lg:pt-20 xl:pt-36
            """.trimIndent()
        ) {
            smallWrap(smallPage) {
                h1("mb-2") { +title }
                linkWheel(pageName)
                if (desc != null) div("pt-2") {
                    p("text-zinc-400 m-0") { i { +desc } }
                }
                hr("my-4")
                init()
                if (showFooter) {
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
