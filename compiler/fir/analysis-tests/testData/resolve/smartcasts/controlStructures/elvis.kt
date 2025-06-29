// RUN_PIPELINE_TILL: BACKEND
// DUMP_CFG
interface A {
    fun foo()
    val b: Boolean
}

fun test_1(x: A?) {
    if (x?.b ?: return) {
        x.foo()
    }
}

fun test2(a: Any?, b: Any?): String {
    if (b !is String) return ""
    if (a !is String?) return ""
    return a ?: b
}

/* GENERATED_FIR_TAGS: elvisExpression, functionDeclaration, ifExpression, interfaceDeclaration, isExpression,
nullableType, propertyDeclaration, safeCall, smartcast, stringLiteral */
