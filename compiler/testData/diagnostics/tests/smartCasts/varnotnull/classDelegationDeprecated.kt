// RUN_PIPELINE_TILL: BACKEND
// LATEST_LV_DIFFERENCE
// ISSUE: KT-57417

interface HasProperty {
    val property: Int?
}

class Test(delegate: HasProperty) : HasProperty by delegate

fun test(a: Test) {
    if (a.property != null) <!DEBUG_INFO_SMARTCAST!>a.property<!> + 1
}

/* GENERATED_FIR_TAGS: additiveExpression, classDeclaration, equalityExpression, functionDeclaration, ifExpression,
inheritanceDelegation, integerLiteral, interfaceDeclaration, nullableType, primaryConstructor, propertyDeclaration,
smartcast */
