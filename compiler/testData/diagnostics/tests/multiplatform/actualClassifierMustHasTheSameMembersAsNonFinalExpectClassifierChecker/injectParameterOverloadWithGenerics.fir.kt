// LATEST_LV_DIFFERENCE
// RUN_PIPELINE_TILL: BACKEND
// MODULE: m1-common
// FILE: common.kt

open class Base<T> {
    open fun foo(t: T) {}
}

expect open class Foo<R> : Base<R>

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual open class Foo<R>() : Base<R>() {
    <!ACCIDENTAL_OVERRIDE!>fun <T> foo(t: T) {}<!>
}

/* GENERATED_FIR_TAGS: actual, classDeclaration, expect, functionDeclaration, nullableType, primaryConstructor,
typeParameter */
