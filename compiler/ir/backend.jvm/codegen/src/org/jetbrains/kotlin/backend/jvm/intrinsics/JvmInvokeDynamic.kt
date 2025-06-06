/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm.intrinsics

import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin
import org.jetbrains.kotlin.backend.jvm.codegen.*
import org.jetbrains.kotlin.backend.jvm.ir.getBooleanConstArgument
import org.jetbrains.kotlin.backend.jvm.ir.getIntConstArgument
import org.jetbrains.kotlin.backend.jvm.ir.getStringConstArgument
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.render
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode
import org.jetbrains.org.objectweb.asm.Handle
import org.jetbrains.org.objectweb.asm.Type

object JvmInvokeDynamic : IntrinsicMethod() {
    override fun invoke(expression: IrFunctionAccessExpression, codegen: ExpressionCodegen, data: BlockInfo): PromisedValue {
        fun fail(message: String): Nothing =
            throw AssertionError("$message; expression:\n${expression.dump()}")

        val dynamicCall = expression.arguments[0] as? IrCall
            ?: fail("'dynamicCall' is expected to be a call")
        val dynamicCallee = dynamicCall.symbol.owner
        if (dynamicCallee.parent != codegen.context.symbols.kotlinJvmInternalInvokeDynamicPackage ||
            dynamicCallee.origin != JvmLoweredDeclarationOrigin.INVOKEDYNAMIC_CALL_TARGET
        )
            fail("Unexpected dynamicCallee: '${dynamicCallee.render()}'")

        val bootstrapMethodHandleArg = expression.arguments[1] as? IrCall
            ?: fail("'bootstrapMethodHandle' should be a call")
        val bootstrapMethodHandle = evalMethodHandle(bootstrapMethodHandleArg)

        val bootstrapMethodArgs = expression.arguments[2] as? IrVararg
            ?: fail("'bootstrapMethodArgs' is expected to be a vararg")
        val asmBootstrapMethodArgs = bootstrapMethodArgs.elements
            .map { generateBootstrapMethodArg(it, codegen) }
            .toTypedArray()

        val dynamicCalleeMethod = codegen.methodSignatureMapper.mapAsmMethod(dynamicCallee)
        val dynamicCallGenerator = IrCallGenerator.DefaultCallGenerator
        val dynamicCalleeArgumentTypes = dynamicCalleeMethod.argumentTypes
        for (i in dynamicCallee.parameters.indices) {
            val dynamicCalleeParameter = dynamicCallee.parameters[i]
            val dynamicCalleeArgument = dynamicCall.arguments[i]
                ?: fail("No argument #$i in 'dynamicCall'")
            val dynamicCalleeArgumentType = dynamicCalleeArgumentTypes.getOrElse(i) {
                fail("No argument type #$i in dynamic callee: $dynamicCalleeMethod")
            }
            dynamicCallGenerator.genValueAndPut(dynamicCalleeParameter, dynamicCalleeArgument, dynamicCalleeArgumentType, codegen, data)
        }

        codegen.mv.invokedynamic(dynamicCalleeMethod.name, dynamicCalleeMethod.descriptor, bootstrapMethodHandle, asmBootstrapMethodArgs)

        return MaterialValue(codegen, dynamicCalleeMethod.returnType, expression.type)
    }

    private fun generateBootstrapMethodArg(element: IrVarargElement, codegen: ExpressionCodegen): Any =
        when (element) {
            is IrRawFunctionReference ->
                generateMethodHandle(element, codegen)
            is IrClassReference ->
                generateClassReference(element, codegen)
            is IrCall ->
                evalBootstrapArgumentIntrinsicCall(element, codegen)
                    ?: throw AssertionError("Unexpected callee in bootstrap method argument:\n${element.dump()}")
            is IrConst ->
                when (element.kind) {
                    IrConstKind.Byte -> (element.value as Byte).toInt()
                    IrConstKind.Short -> (element.value as Short).toInt()
                    IrConstKind.Int -> element.value as Int
                    IrConstKind.Long -> element.value as Long
                    IrConstKind.Float -> element.value as Float
                    IrConstKind.Double -> element.value as Double
                    IrConstKind.String -> element.value as String
                    else ->
                        throw AssertionError("Unexpected constant expression in bootstrap method argument:\n${element.dump()}")
                }
            else ->
                throw AssertionError("Unexpected bootstrap method argument:\n${element.dump()}")
        }

    private fun evalBootstrapArgumentIntrinsicCall(irCall: IrCall, codegen: ExpressionCodegen): Any? {
        return when (irCall.symbol) {
            codegen.context.symbols.jvmOriginalMethodTypeIntrinsic ->
                evalOriginalMethodType(irCall, codegen)
            codegen.context.symbols.jvmMethodType ->
                evalMethodType(irCall)
            codegen.context.symbols.jvmMethodHandle ->
                evalMethodHandle(irCall)
            else ->
                null
        }
    }

    private fun evalMethodType(irCall: IrCall): Type {
        val descriptor = irCall.getStringConstArgument(0)
        return Type.getMethodType(descriptor)
    }

    private fun evalMethodHandle(irCall: IrCall): Handle {
        val tag = irCall.getIntConstArgument(0)
        val owner = irCall.getStringConstArgument(1)
        val name = irCall.getStringConstArgument(2)
        val descriptor = irCall.getStringConstArgument(3)
        val isInterface = irCall.getBooleanConstArgument(4)
        return Handle(tag, owner, name, descriptor, isInterface)
    }

    private fun generateMethodHandle(irRawFunctionReference: IrRawFunctionReference, codegen: ExpressionCodegen): Handle =
        codegen.methodSignatureMapper.mapToMethodHandle(irRawFunctionReference.symbol.owner)

    private fun generateClassReference(classRef: IrClassReference, codegen: ExpressionCodegen) =
        codegen.typeMapper.mapType(classRef.classType, TypeMappingMode.INVOKE_DYNAMIC_BOOTSTRAP_ARGUMENT)

    private fun evalOriginalMethodType(irCall: IrCall, codegen: ExpressionCodegen): Type {
        val irRawFunRef = irCall.arguments[0] as? IrRawFunctionReference
            ?: throw AssertionError(
                "Argument in ${irCall.symbol.owner.name} call is expected to be a raw function reference:\n" +
                        irCall.dump()
            )
        val irFun = irRawFunRef.symbol.owner
        val asmMethod = codegen.methodSignatureMapper.mapAsmMethod(irFun)
        return Type.getMethodType(asmMethod.descriptor)
    }
}
