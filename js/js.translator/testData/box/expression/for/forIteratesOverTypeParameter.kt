// KJS_WITH_FULL_RUNTIME
public fun <T, C : Collection<T>> bar(collection: C, f: (T) -> Unit) { for (item in collection) f(item) }

fun box(): String {
    val collection = listOf("O", "K")
    var result = ""
    bar(collection) { result += it }
    return result
}