// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER -NOT_YET_SUPPORTED_IN_INLINE

inline fun<reified T> foo(x: T) {
    fun<<!REIFIED_TYPE_PARAMETER_NO_INLINE!>reified<!> R> bar() {

    }

    bar<T>()
}

/* GENERATED_FIR_TAGS: functionDeclaration, inline, localFunction, nullableType, reified, typeParameter */
