# Personal website

My personal static website, generated using Kotlin and TailwindCSS. It's an unusual setup but lets me quickly write templates and develop locally with hot-reload thanks to Ktor!

The goal is to generate tiny pages without any client-side Javascript, while still being able to run complex code to, say list all blog entries and group them by year, or split common components into their own reusable functions. Tailwind to act similar to Jetpack Compose's modifier system to let us define styles inside each component in an independant way.

## Tools used

- [kotlinx.html](https://github.com/Kotlin/kotlinx.html) for writing page templates with reusable components.
- [TailwindCSS](https://tailwindcss.com/), running via npx to generate all site css.
- Jetbrains' [markdown parser](https://github.com/JetBrains/markdown) for parsing pages to HTML.
- [Ktor](https://ktor.io/) for serving files locally with hot-reload support.
- Other Kotlin goodies we're used to like serialization, datetime, used across site templates

## Project structure

- `src/.../Routing.kt` defines a simple dsl structure for setting up page routes.
- There's a parser for converting these to Ktor routes for local development, and html files for final static site generation.
- Markdown files for pages to be templated are stored under `pages`, these don't specify templates themselves, rather there are utility functions to parse all subpages under a path and use them templates, also defined in `Routing.kt`.
- Reusable components are stored under `src/.../components`, these use FlowContent as a broad receiver so they can be called from most HTML tags.

## Build instructions

- Run `npm install` to get tailwind.
- Generate the static files with `./gradlew generate`
- Serve locally for hot-reloads using the provided serve run configuration in Intellij (this will directly run a function that launches an HTTP server and starts a continuous Gradle build, it doesn't seem possible to run the server and continuous build together as a single gradle task.)

Note that Ktor's hot reload is not perfect, some things like lambda references don't automatically get updated, so in site routing we need to reference templates as KFunction objects.

## License

[CC0](LICENSE): This work has been marked as dedicated to the public domain. You can do whatever you want with it, attribution is super appreciated.
