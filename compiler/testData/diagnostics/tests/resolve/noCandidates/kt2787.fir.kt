// RUN_PIPELINE_TILL: FRONTEND
interface MutableMatrix<T> {
}

fun <T> toMutableMatrix(): MutableMatrix<T> {
    return <!INTERFACE_AS_FUNCTION!>MutableMatrix<!><T>()
}

/* GENERATED_FIR_TAGS: functionDeclaration, interfaceDeclaration, nullableType, typeParameter */
