package me.dvyy.www

import kotlinx.html.HTML
import me.dvyy.www.pages.home
import me.dvyy.www.pages.projects


val routing = siteRoot {
    "index" shows HTML::home
    "projects" shows HTML::projects
//    "markdown" shows markdown(
//        """
//        # Hello, world!
//        """.trimIndent()
//    )
//    "about" {
//        "me" shows markdown("## About me")
//        "you" shows markdown("## About you")
//    }
}
