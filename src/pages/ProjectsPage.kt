package pages

import components.outlinedChip
import icons
import kotlinx.html.*
import me.dvyy.shocky.markdown
import me.dvyy.shocky.md
import me.dvyy.shocky.page.Page
import org.intellij.lang.annotations.Language
import templates.blog.chipList
import templates.defaultTemplate

fun Page.projectsPage() = defaultTemplate {
    project(
        tags = "GPU Compute-Shaders Kotlin",
        heading = "GPU Particle Simulation",
        source = "https://github.com/0ffz/particles",
    ) {
        this md """
        A [molecular dynamics](https://en.wikipedia.org/wiki/Molecular_dynamics) simulation written using GPU compute shaders.
        These can run efficiently on most consumer hardware, including [in browser](https://particles.dvyy.me/) using WebGPU. Users can interact with particles live, changing parameters like time step, heat, or even the forces between particles. Some pairwise atomic potentials are provided by default, with an API for users to add more *(read the [docs](https://particles.dvyy.me/docs).)*
        """.trimIndent()

        captionedImage(
            "min-h-60 md:min-h-80 object-cover",
            src = "assets/images/particles.webp",
            caption = "A 3D scene with two particle types, plotting the size distribution of clusters in the sidebar."
        )
    }

    project(
        tags = "ECS GameDev Kotlin",
        heading = "Geary", source = "https://github.com/MineInAbyss/Geary"
    ) {
        this md """
        An entity component system I wrote from the ground up in Kotlin. It lets users write fast code in a modular way by making systems that quickly iterate over entities with desired data. It uses archetypes for its architecture, and the design would allow for data to be packed tightly in memory once value objects are added to Java. It also has entity relations built into the engine, inspired by [flecs](https://github.com/SanderMertens/flecs).
    
        *You can see a demo in my blog post [Making an efficient Minecraft particle system using ECS](blog/geary/particle-system)*
        """.trimIndent()
    }

    project(
        tags = "Docker Ansible Grafana Linux",
        heading = "Sysadmin work at *[Mine in Abyss](https://mineinabyss.com/)*"
    ) {
        this md """
        A community-driven recreation of Made in Abyss in Minecraft. I did game development and server infrastructure work across several projects that help us deploy and maintain servers as a small team, here's a quick summary of my work there:
    
        - Created [Keepup](https://github.com/MineInAbyss/Keepup), a CLI tool to manage server plugin dependencies and configs, including support for templates with minimum startup time overhead.
        - Worked extensively with [Grafana](https://grafana.com/) to set up server monitoring that's helped greatly with diagnosing tps issues. I've also made a small Paper plugin to easily set up [Pyroscope](https://github.com/MineInAbyss/pyroscope-mc/) for continuous profiling.
        - Automated machine provisioning with an [Ansible playbook](https://github.com/MineInAbyss/ansible-in-abyss) that sets up a new server and services via Docker compose.
        - I also made a [per-server config propagation playbook](https://github.com/MineInAbyss/server-config) that pulls and copies config updates from GitHub, including private repositories for things like custom models.
        - Made [custom Docker images](https://github.com/MineInAbyss/Docker) that help tie all our tools into one package that's useful for production and local development.
        """.trimIndent()
    }

    captionedImage(
        "full-bleed min-h-60 md:min-h-80 object-cover",
        src = "assets/images/orth.png",
        caption = "The golden bridge in Orth overlooks the Abyss."
    )

    project(
        tags = "Android Compose Sqldelight Kotlin",
        heading = "Weekly task planner",
        source = "https://github.com/0ffz/tasks"
    ) {
        this md """
        A cross-platform task and week planning app written on Jetpack Compose, with a sync server.
        It uses sqlite for storage, with near-instant startup times on Android, alongside a quick-add feature.
        """.trimIndent()
        captionedImage(src = "assets/images/tasks-desktop.png", caption = "The application home screen on Desktop")
    }

    project(
        tags = "Unity C#",
        heading = "Game jams"
    ) {
        this md """
        I've worked in Unity and Godot with C# to create games for game jams.
        My latest entry was [Rock Bottom](https://github.com/0ffz/Ludum-Dare-46) in Ludum Dare 46.
        I worked in a team of three to create a physics puzzle game using an event driven architecture.
        """.trimIndent()
        captionedImage(src = "assets/images/rock_bottom.avif", caption = "Pet rocks in the pet rock shop return home")
    }

    project(
        tags = "TailwindCSS Django",
        heading = "Frontend roundup",
    ) {
        this md """
        Here are a few of my projects involving frontend work:

        - The [Mine in Abyss website](https://mineinabyss.com/) (as well as [this very website](https://dvyy.me)) are both built using [Shocky](https://github.com/0ffz/shocky) a mini static site generator I wrote in Kotlin. I can split things into reusable components without any client side JavaScript required. Learn more on its [project page](https://github.com/0ffz/personal-site).
        - [Clicky](https://github.com/0ffz/Clicky), an online vote room I made for a professor that wanted students to be able to answer impromptu questions in class. It's built on Django and the frontend is mostly pure HTML with skeleton CSS applied.
        """.trimIndent()
    }
}

fun FlowContent.project(
    tags: String = "",
    @Language("markdown")
    heading: String,
    source: String? = null,
    languages: String = "",
    @Language("markdown")
    content: FlowContent.() -> Unit,
) {
    markdown("## $heading")
    chipList {
        if (source != null) outlinedChip("Source", url = source)
        div("pl-2") { icons.tags }
        tags.split(" ").forEach { outlinedChip(it) }
        languages.split(" ").forEach { languageIcon(it) }
    }
    content()
}

fun FlowContent.languageIcon(lang: String) = div("pl-2") {
    when (lang) {
        "Kotlin" -> icons.langKotlin
        "C#" -> icons.langCSharp
    }
}

fun FlowContent.captionedImage(
    classes: String = "",
    src: String,
    caption: String,
) {
    img(classes = classes, alt = caption, src = src, loading = ImgLoading.lazy) { }
    figcaption { +caption }
}
