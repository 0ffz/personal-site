import me.dvyy.www.sitegen.StaticGenerator
import kotlin.io.path.Path

fun main() {
    StaticGenerator(
        dest = Path("out"),
        root = routing,
        extraInputs = listOf(Path("site/assets")),
        devMode = System.getenv("DEVELOPMENT") == "true"
    ).generate()
}
