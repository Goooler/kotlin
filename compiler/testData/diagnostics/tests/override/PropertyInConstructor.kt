// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
open class Base {
    protected open val prot: Int = 1
    internal open val int: Int = 1
    public open val pub: Int = 1
}

class Child(
    override val prot: Int,
    override val int: Int,
    override val pub: Int
) : Base()

/* GENERATED_FIR_TAGS: classDeclaration, integerLiteral, override, primaryConstructor, propertyDeclaration */
