// RUN_PIPELINE_TILL: FRONTEND
// Properties can be recursively annotated
annotation class ann(val x: Int)
class My {
    @ann(<!ANNOTATION_ARGUMENT_MUST_BE_CONST!>x<!>) val x: Int = 1
}

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, integerLiteral, primaryConstructor, propertyDeclaration */
