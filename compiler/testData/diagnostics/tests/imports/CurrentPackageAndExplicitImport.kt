// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: a.kt
package a

open class Y

// FILE: b.kt
package b

class X

// FILE: b1.kt
package b

import a.Y as X

class Y : X() // class from explicit import should take priority

/* GENERATED_FIR_TAGS: classDeclaration */
