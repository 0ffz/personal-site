package me.dvyy.www.generation

import kotlinx.html.HTML

class SiteRouteDSL(val name: String) {
    val subRoutes: MutableList<SiteRoute> = mutableListOf()
    operator fun String.invoke(block: SiteRouteDSL.() -> Unit) {
        subRoutes.add(SiteRouteDSL(this).apply(block).build())
    }

    infix fun String.shows(html: Function1<HTML, Unit>) {
        subRoutes.add(SiteRoute.Document(this, html))
    }
//    infix fun String.shows(html: HtmlBuilder) {
//        subRoutes.add(SiteRoute.Document(this, html))
//    }

    fun build() = SiteRoute.Route(name, subRoutes)
}

inline fun siteRoot(crossinline block: SiteRouteDSL.() -> Unit) = SiteRouteDSL("/").apply(block).build()
