import me.dvyy.www.sitegen.StaticGenerator
import kotlin.io.path.Path

fun main() {
    StaticGenerator(
        output = Path("out"),
        routing = routing,
        extraInputs = listOf(Path("site/assets"))
    ).generate()
}
