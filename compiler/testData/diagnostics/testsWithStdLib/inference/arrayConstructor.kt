// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE

package b

import checkSubtype

fun bar() {
    val a1 = Array(1, {i: Int -> i})
    val a2 = Array(1, {i: Int -> "$i"})
    val a3 = Array(1, {it})

    checkSubtype<Array<Int>>(a1)
    checkSubtype<Array<String>>(a2)
    checkSubtype<Array<Int>>(a3)
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, functionalType, infix,
integerLiteral, lambdaLiteral, localProperty, nullableType, propertyDeclaration, typeParameter, typeWithExtension */
