// RUN_PIPELINE_TILL: FRONTEND
fun foo(a: Int) {
    !<!UNRESOLVED_REFERENCE!>bbb<!>
    <!UNRESOLVED_REFERENCE!>bbb<!> + a
}

/* GENERATED_FIR_TAGS: additiveExpression, functionDeclaration */
