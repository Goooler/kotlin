// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_VARIABLE -UNUSED_PARAMETER -TOPLEVEL_TYPEALIASES_ONLY

fun outer() {
    typealias Test1 = <!RECURSIVE_TYPEALIAS_EXPANSION!>Test1<!>
    typealias Test2 = <!RECURSIVE_TYPEALIAS_EXPANSION!>List<Test2><!>
    typealias Test3<T> = List<<!UNRESOLVED_REFERENCE!>Test3<!><T>>
}

/* GENERATED_FIR_TAGS: functionDeclaration, nullableType, typeAliasDeclaration, typeAliasDeclarationWithTypeParameter,
typeParameter */
