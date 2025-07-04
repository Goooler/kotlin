// RUN_PIPELINE_TILL: FRONTEND
class A(val next: A? = null) {
    val x: String
    init {
        next?.<!VAL_REASSIGNMENT!>x<!> = "a"
        x = "b"
        this.<!VAL_REASSIGNMENT!>x<!> = "c"
        <!VAL_REASSIGNMENT!>x<!> = "d" // don't repeat the same diagnostic again with this receiver
        this.<!VAL_REASSIGNMENT!>x<!> = "e"

        next?.<!VAL_REASSIGNMENT!>x<!> = "f"
    }
}

/* GENERATED_FIR_TAGS: assignment, classDeclaration, init, nullableType, primaryConstructor, propertyDeclaration,
safeCall, stringLiteral, thisExpression */
