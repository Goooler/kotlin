// RUN_PIPELINE_TILL: FRONTEND
annotation class Ann(val a: Array<String>)

val foo = ""
var bar = 1
const val cnst = 2

@Ann(
    <!NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION!>arrayOf(
        <!NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION!>foo<!>,
        <!NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION!>foo + cnst.toString()<!>
    )<!>
)
fun test() {}

const val A = "foo"
const val B = 100

annotation class S(val s: String)

@S(A + B)
fun foo() {}

/* GENERATED_FIR_TAGS: additiveExpression, annotationDeclaration, collectionLiteral, const, functionDeclaration,
integerLiteral, primaryConstructor, propertyDeclaration, stringLiteral */
