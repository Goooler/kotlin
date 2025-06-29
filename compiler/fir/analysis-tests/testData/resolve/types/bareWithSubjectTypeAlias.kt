// RUN_PIPELINE_TILL: FRONTEND
sealed class A<out T> {
    class B<out T1>(val x: T1) : A<T1>()
    class C<out T2>(val y: T2) : A<T2>()
}

typealias TA = A<CharSequence>

fun bar(): TA = TODO()

fun foo() {
    <!NO_ELSE_IN_WHEN!>when<!> (val a = bar()) {
        is A.B -> a.x.length
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, isExpression, localProperty, nestedClass, nullableType,
out, primaryConstructor, propertyDeclaration, sealed, smartcast, typeAliasDeclaration, typeParameter, whenExpression,
whenWithSubject */
