// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// LANGUAGE: +SuspendConversion
// DIAGNOSTICS: -UNUSED_PARAMETER

fun interface SuspendRunnable {
    suspend fun invoke()
}

fun foo1(s: SuspendRunnable) {}
fun bar1() {}

fun bar2(s: String = ""): Int = 0

fun bar3() {}
suspend fun bar3(s: String = ""): Int = 0

fun test() {
    foo1(::bar1)
    foo1(::bar2)

    foo1(::bar3) // Should be ambiguity
}

/* GENERATED_FIR_TAGS: callableReference, funInterface, functionDeclaration, integerLiteral, interfaceDeclaration,
samConversion, stringLiteral, suspend */
