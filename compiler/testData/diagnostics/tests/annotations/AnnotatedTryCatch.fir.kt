// RUN_PIPELINE_TILL: FRONTEND
annotation class My

fun foo(arg: Int): Int {
    try {
        return 1 / (arg - arg)
    } catch (e: <!WRONG_ANNOTATION_TARGET!>@My<!> Exception) {
        return -1
    }
}

/* GENERATED_FIR_TAGS: additiveExpression, annotationDeclaration, functionDeclaration, integerLiteral, localProperty,
multiplicativeExpression, propertyDeclaration, tryExpression */
