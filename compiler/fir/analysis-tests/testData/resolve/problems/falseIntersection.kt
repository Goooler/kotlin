// RUN_PIPELINE_TILL: BACKEND
interface A {
    fun foo() {}
}

interface B : A {
    override fun foo() {}
}

// We should not have intersection override foo() in this class
class C : B, A

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, interfaceDeclaration, override */
