// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VARIABLE

fun test() {
    val bug = storing { "" }.default(null)
}

class Bar<out T>

fun <T> Bar<T>.default(defaultValue: T): Bar<T> = TODO()
fun <T> Bar<T>.default(defaultValue: () -> T): Bar<T> = TODO()

fun <T> storing(transform: String.() -> T): Bar<T> = TODO()

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, functionalType, lambdaLiteral,
localProperty, nullableType, out, propertyDeclaration, stringLiteral, typeParameter, typeWithExtension */
