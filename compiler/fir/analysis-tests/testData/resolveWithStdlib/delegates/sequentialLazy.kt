// RUN_PIPELINE_TILL: BACKEND
class Some(classNames: () -> Collection<String>) {
    internal val first by lazy {
        classNames().toSet()
    }

    private val second by lazy {
        val nonDeclaredNames = getNonDeclaredClassifierNames() ?: return@lazy null
        val allNames = first + nonDeclaredNames
        allNames
    }

    fun getNonDeclaredClassifierNames(): Set<String>? = null
}

/* GENERATED_FIR_TAGS: additiveExpression, classDeclaration, elvisExpression, functionDeclaration, functionalType,
lambdaLiteral, localProperty, nullableType, primaryConstructor, propertyDeclaration, propertyDelegate */
