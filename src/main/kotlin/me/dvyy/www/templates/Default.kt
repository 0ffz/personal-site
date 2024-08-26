package me.dvyy.www.templates

import kotlinx.html.*
import me.dvyy.www.components.linkWheel
import me.dvyy.www.markdown

inline fun HTML.defaultTemplate(
    pageName: String,
    smallPage: Boolean = false,
    showFooter: Boolean = true,
    crossinline init: FlowContent.() -> Unit,
) {
    head {
        script(src = "https://cdn.tailwindcss.com") {}
    }
    body(classes = "bg-zinc-900 min-h-screen") {
        div(
            """max-w-none
            [&_a]:text-purple-300 [&_a]:no-underline hover:[&_a]:text-purple-200
            [&_h2]:mt-8 [&_h3]:mt-6 [&_h4]:mt-4
            # Set spacing
            [&_p]:mt-3 [&_p]:mb-3
            [&_hr]:border-zinc-700
            """.trimIndent()
        ) {

            div("items-center h-auto px-4 pt-4 md:pt-8 md:px-8 lg:pt-20 xl:pt-36 text-slate-200 mx-auto max-w-screen-lg") {
                div(if (smallPage) "max-w-xl" else "max-w-screen-lg") {
                    h1("text-3xl mb-2") { +pageName }
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
