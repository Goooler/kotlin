// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// SKIP_TXT
// FILE: TaskProvider.java
public interface TaskProvider<T extends CharSequence> {
    void configure(Action<? super T> a);
}

// FILE: main.kt
fun interface Action<E> {
    fun E.exec()
}

fun foo(tp: TaskProvider<out CharSequence>) {
    tp.configure {
        length
    }
}

/* GENERATED_FIR_TAGS: flexibleType, funInterface, funWithExtensionReceiver, functionDeclaration, inProjection,
interfaceDeclaration, javaType, lambdaLiteral, nullableType, outProjection, samConversion, typeParameter */
