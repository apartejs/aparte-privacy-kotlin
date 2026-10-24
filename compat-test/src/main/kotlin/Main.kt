import dev.apartejs.privacy.Privacy

fun main() {
    val privacy = Privacy.load()
    val found = privacy.detect("Appelle Marie Lambert au 06 12 34 56 78")
    check(found.any { it.subtype == "private_person" }) { "no person found: $found" }
    println("compat ok: ${found.map { it.subtype }}")
}
