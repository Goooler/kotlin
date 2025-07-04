// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// WITH_STDLIB
// ISSUE: KT-66748

abstract class Base
interface Some
typealias Other = Some

abstract class A : Base(), AutoCloseable {
    override fun toString() = super.toString()
}

abstract class B : Base(), Other {
    override fun toString() = super.toString()
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, interfaceDeclaration, override, superExpression,
typeAliasDeclaration */
