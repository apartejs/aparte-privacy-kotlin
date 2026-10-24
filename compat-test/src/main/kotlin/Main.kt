import dev.apartejs.privacy.Privacy

fun main() {
    val privacy = Privacy.load()
    val found = privacy.detect("My name is John Smith and my email is john.smith@gmail.com")
    check(found.any { it.subtype == "private_person" }) { "no person found: $found" }
    println("compat ok: ${found.map { it.subtype }}")
}
