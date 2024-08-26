package me.dvyy.www.templates

import kotlinx.html.*
import me.dvyy.www.components.linkWheel

inline fun HTML.defaultTemplate(
    pageName: String,
    title: String = pageName,
    smallPage: Boolean = false,
    showFooter: Boolean = true,
    crossinline init: FlowContent.() -> Unit,
) {
    head {
        meta(charset ="utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link(rel = "stylesheet", href = "/static/tailwind/styles.css")
        title(title)
    }
    body(classes = "bg-zinc-900 min-h-screen") {
        div(
            """
                prose prose-zinc dark:prose-invert max-w-none
                prose-a:text-purple-300 prose-a:no-underline hover:prose-a:text-purple-200
                prose-h2:mt-8 prose-h2:mb-1 prose-h3:mt-6 prose-h3:mb-1 prose-h4:mt-4 prose-h4:mb-1
            """.trimIndent()
        ) {

            div("items-center h-auto px-4 pt-4 md:pt-8 md:px-8 lg:pt-20 xl:pt-36 text-slate-200 mx-auto max-w-screen-lg") {
                div(if (smallPage) "max-w-xl" else "max-w-screen-lg") {
                    h1("mb-2") { +title }
                    linkWheel(pageName)
                    hr("my-5")
                    init()
                    if (showFooter) {
                        div("md:h-36")
                    }
                }
            }
        }
    }
}
