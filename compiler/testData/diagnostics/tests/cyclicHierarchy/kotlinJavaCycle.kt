// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// FILE: J.java

class J extends K {
    void foo() {}
}

// FILE: K.kt

class K : <!CYCLIC_INHERITANCE_HIERARCHY!>J<!>() {
    fun bar() {}
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, javaType */
