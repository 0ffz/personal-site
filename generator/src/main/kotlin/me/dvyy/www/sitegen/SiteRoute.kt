package me.dvyy.www.sitegen

import kotlinx.html.HTML

sealed interface SiteRoute {
    val name: String

    data class Route(override val name: String, val subRoutes: List<SiteRoute>) : SiteRoute
    data class Document(override val name: String, val html: (HTML) -> Unit) : SiteRoute
}
