// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// LANGUAGE: +InlineClasses
// DIAGNOSTICS: -INLINE_CLASS_DEPRECATED
// FILE: kt1.kt
package kt

inline class Z(val value: Int)

interface IFoo<T> {
    fun foo(): T
}

open class KFooZ : IFoo<Z> {
    override fun foo(): Z = Z(42)
}

// FILE: j/J.java
package j;

import kt.Z;
import kt.KFooZ;

public class J extends KFooZ {
}

// FILE: kt2.kt
package kt

import j.J

fun jfoo(x: J) = x.foo()

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, integerLiteral, interfaceDeclaration, javaType,
nullableType, override, primaryConstructor, propertyDeclaration, typeParameter */
