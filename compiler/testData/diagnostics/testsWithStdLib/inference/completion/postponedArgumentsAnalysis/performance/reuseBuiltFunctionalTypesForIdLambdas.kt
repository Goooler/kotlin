// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// SKIP_TXT
// DIAGNOSTICS: -UNUSED_ANONYMOUS_PARAMETER

fun <T> id(x: T) = x

class A {
    val x: Map<String, (String, String, String, String) -> Unit> =
        mapOf(
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> },
            "" to id { a, b, c, d -> }
        )
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, lambdaLiteral, nullableType, propertyDeclaration,
stringLiteral, typeParameter */
