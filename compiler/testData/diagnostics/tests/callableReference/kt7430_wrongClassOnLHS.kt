// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_EXPRESSION

class Unrelated()

class Test(val name: String = "") {
    init {
        Unrelated::<!UNRESOLVED_REFERENCE!>name<!>
        Unrelated::<!UNRESOLVED_REFERENCE!>foo<!>
    }

    fun foo() {}
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, init, primaryConstructor, propertyDeclaration,
stringLiteral */
