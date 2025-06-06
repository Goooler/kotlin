// This file was generated automatically. See  generateTestDataForTypeScriptWithFileExport.kt
// DO NOT MODIFY IT MANUALLY.

// CHECK_TYPESCRIPT_DECLARATIONS
// RUN_PLAIN_BOX_FUNCTION
// SKIP_NODE_JS
// INFER_MAIN_MODULE
// MODULE: JS_TESTS
// FILE: interfaces.kt

@file:JsExport

package foo

// Classes


interface TestInterface {
    val value: String
    fun getOwnerName(): String
}


interface AnotherExportedInterface


open class TestInterfaceImpl(override val value: String) : TestInterface {
    override fun getOwnerName() = "TestInterfaceImpl"
}


class ChildTestInterfaceImpl(): TestInterfaceImpl("Test"), AnotherExportedInterface


fun processInterface(test: TestInterface): String {
    return "Owner ${test.getOwnerName()} has value '${test.value}'"
}


external interface OptionalFieldsInterface {
    val required: Int
    val notRequired: Int?
}


interface WithTheCompanion {
    val interfaceField: String

    companion object {
        fun companionFunction(): String = "FUNCTION"
    }
}


fun processOptionalInterface(a: OptionalFieldsInterface): String {
    return "${a.required}${a.notRequired ?: "unknown"}"
}

// KT-63184

interface InterfaceWithCompanion {
    // Emulate added by plugin companion like kotlinx.serialization does
    @Suppress("WRONG_EXPORTED_DECLARATION")
    @JsExport.Ignore
    companion object {
        fun foo() = "String"
    }
}

// KT-64708

external interface ExportedParentInterface


interface ExportedChildInterface : ExportedParentInterface {
    fun bar()
}

// KT-63907

interface InterfaceWithDefaultArguments {
    fun foo(x: Int = 0) = x
    fun bar(x: Int = 0) = x
}


class ImplementorOfInterfaceWithDefaultArguments : InterfaceWithDefaultArguments {
    override fun bar(x: Int) = x + 1
}