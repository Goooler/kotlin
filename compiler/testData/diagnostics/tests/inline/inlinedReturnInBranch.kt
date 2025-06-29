// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// https://youtrack.jetbrains.com/issue/KT-49289

inline fun <T> myRun(f: () -> T): T = f()

fun foo(): Int {
    myRun {
        return 24
    }
}

fun bar(arg: Boolean): Int {
    if (arg) {
        return 42
    } else {
        myRun {
            return 24
        }
    }
}

/* GENERATED_FIR_TAGS: functionDeclaration, functionalType, ifExpression, inline, integerLiteral, lambdaLiteral,
nullableType, typeParameter */
