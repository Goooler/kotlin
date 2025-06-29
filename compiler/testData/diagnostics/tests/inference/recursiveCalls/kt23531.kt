// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

class Scope

fun <T> simpleAsync0(block: Scope.() -> T) {}
fun <T> simpleAsync1(block: suspend Scope.() -> T) {}
suspend fun <T> simpleAsync2(block: Scope.() -> T) {}
suspend fun <T> simpleAsync3(block: suspend Scope.() -> T) {}

fun insideJob0() = doTheJob0()
fun insideJob1() = doTheJob1()
suspend fun insideJob2() = doTheJob2()
suspend fun insideJob3() = doTheJob3()

fun doTheJob0() = simpleAsync0 { <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!><!DEBUG_INFO_MISSING_UNRESOLVED!>insideJob0<!>()<!> }
fun doTheJob1() = simpleAsync1 { <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!><!DEBUG_INFO_MISSING_UNRESOLVED!>insideJob1<!>()<!> }
suspend fun doTheJob2() = simpleAsync2 { <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!><!DEBUG_INFO_MISSING_UNRESOLVED!>insideJob2<!>()<!> }
suspend fun doTheJob3() = simpleAsync3 { <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_ERROR!><!DEBUG_INFO_MISSING_UNRESOLVED!>insideJob3<!>()<!> }

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, functionalType, lambdaLiteral, nullableType, suspend,
typeParameter, typeWithExtension */
