// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// KT-47135

fun test2() {
    class LocalA {
        inner class LocalB {
            inner class LocalC {
            }
        }
    }

    fun LocalA.LocalB.blah() {
        val c: LocalA.LocalB.LocalC = LocalC()
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, inner, localClass, localFunction,
localProperty, propertyDeclaration */
