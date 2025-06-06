/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir

import org.jetbrains.kotlin.builtins.PrimitiveType
import org.jetbrains.kotlin.builtins.UnsignedType
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.ir.builders.declarations.addConstructor
import org.jetbrains.kotlin.ir.builders.declarations.buildClass
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment
import org.jetbrains.kotlin.ir.declarations.IrFactory
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl
import org.jetbrains.kotlin.ir.util.addFakeOverrides
import org.jetbrains.kotlin.ir.util.createThisReceiverParameter
import org.jetbrains.kotlin.ir.util.irError
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.StandardClassIds

/**
 * Symbols for builtins that are available without any context and are not specific to any backend
 * (but specific to the frontend)
 */
@OptIn(InternalSymbolFinderAPI::class)
abstract class IrBuiltIns {
    abstract val symbolFinder: SymbolFinder
    abstract val languageVersionSettings: LanguageVersionSettings

    abstract val irFactory: IrFactory

    abstract val anyType: IrType
    abstract val anyClass: IrClassSymbol
    abstract val anyNType: IrType
    abstract val booleanType: IrType
    abstract val booleanClass: IrClassSymbol
    abstract val charType: IrType
    abstract val charClass: IrClassSymbol
    abstract val numberType: IrType
    abstract val numberClass: IrClassSymbol
    abstract val byteType: IrType
    abstract val byteClass: IrClassSymbol
    abstract val shortType: IrType
    abstract val shortClass: IrClassSymbol
    abstract val intType: IrType
    abstract val intClass: IrClassSymbol
    abstract val longType: IrType
    abstract val longClass: IrClassSymbol
    abstract val ubyteClass: IrClassSymbol?
    abstract val ubyteType: IrType
    abstract val ushortClass: IrClassSymbol?
    abstract val ushortType: IrType
    abstract val uintClass: IrClassSymbol?
    abstract val uintType: IrType
    abstract val ulongClass: IrClassSymbol?
    abstract val ulongType: IrType
    abstract val floatType: IrType
    abstract val floatClass: IrClassSymbol
    abstract val doubleType: IrType
    abstract val doubleClass: IrClassSymbol
    abstract val nothingType: IrType
    abstract val nothingClass: IrClassSymbol
    abstract val nothingNType: IrType
    abstract val unitType: IrType
    abstract val unitClass: IrClassSymbol
    abstract val stringType: IrType
    abstract val stringClass: IrClassSymbol
    abstract val charSequenceClass: IrClassSymbol

    abstract val collectionClass: IrClassSymbol
    abstract val arrayClass: IrClassSymbol
    abstract val setClass: IrClassSymbol
    abstract val listClass: IrClassSymbol
    abstract val mapClass: IrClassSymbol
    abstract val mapEntryClass: IrClassSymbol
    abstract val iterableClass: IrClassSymbol
    abstract val iteratorClass: IrClassSymbol
    abstract val listIteratorClass: IrClassSymbol
    abstract val mutableCollectionClass: IrClassSymbol
    abstract val mutableSetClass: IrClassSymbol
    abstract val mutableListClass: IrClassSymbol
    abstract val mutableMapClass: IrClassSymbol
    abstract val mutableMapEntryClass: IrClassSymbol
    abstract val mutableIterableClass: IrClassSymbol
    abstract val mutableIteratorClass: IrClassSymbol
    abstract val mutableListIteratorClass: IrClassSymbol

    abstract val comparableClass: IrClassSymbol
    abstract val throwableType: IrType
    abstract val throwableClass: IrClassSymbol
    abstract val kCallableClass: IrClassSymbol
    abstract val kPropertyClass: IrClassSymbol
    abstract val kClassClass: IrClassSymbol
    abstract val kTypeClass: IrClassSymbol
    abstract val kProperty0Class: IrClassSymbol
    abstract val kProperty1Class: IrClassSymbol
    abstract val kProperty2Class: IrClassSymbol
    abstract val kMutableProperty0Class: IrClassSymbol
    abstract val kMutableProperty1Class: IrClassSymbol
    abstract val kMutableProperty2Class: IrClassSymbol
    abstract val functionClass: IrClassSymbol
    abstract val kFunctionClass: IrClassSymbol
    abstract val annotationType: IrType
    abstract val annotationClass: IrClassSymbol

    // TODO: consider removing to get rid of descriptor-related dependencies
    abstract val primitiveTypeToIrType: Map<PrimitiveType, IrType>

    abstract val primitiveIrTypes: List<IrType>
    abstract val primitiveIrTypesWithComparisons: List<IrType>
    abstract val primitiveFloatingPointIrTypes: List<IrType>

    abstract val byteIterator: IrClassSymbol
    abstract val charIterator: IrClassSymbol
    abstract val shortIterator: IrClassSymbol
    abstract val intIterator: IrClassSymbol
    abstract val longIterator: IrClassSymbol
    abstract val floatIterator: IrClassSymbol
    abstract val doubleIterator: IrClassSymbol
    abstract val booleanIterator: IrClassSymbol

    abstract val byteArray: IrClassSymbol
    abstract val charArray: IrClassSymbol
    abstract val shortArray: IrClassSymbol
    abstract val intArray: IrClassSymbol
    abstract val longArray: IrClassSymbol
    abstract val floatArray: IrClassSymbol
    abstract val doubleArray: IrClassSymbol
    abstract val booleanArray: IrClassSymbol

    abstract val ubyteArray: IrClassSymbol?
    abstract val ushortArray: IrClassSymbol?
    abstract val uintArray: IrClassSymbol?
    abstract val ulongArray: IrClassSymbol?

    abstract val primitiveArraysToPrimitiveTypes: Map<IrClassSymbol, PrimitiveType>
    abstract val primitiveTypesToPrimitiveArrays: Map<PrimitiveType, IrClassSymbol>
    abstract val primitiveArrayElementTypes: Map<IrClassSymbol, IrType?>
    abstract val primitiveArrayForType: Map<IrType?, IrClassSymbol>

    abstract val unsignedTypesToUnsignedArrays: Map<UnsignedType, IrClassSymbol>
    abstract val unsignedArraysElementTypes: Map<IrClassSymbol, IrType?>

    abstract val lessFunByOperandType: Map<IrClassifierSymbol, IrSimpleFunctionSymbol>
    abstract val lessOrEqualFunByOperandType: Map<IrClassifierSymbol, IrSimpleFunctionSymbol>
    abstract val greaterOrEqualFunByOperandType: Map<IrClassifierSymbol, IrSimpleFunctionSymbol>
    abstract val greaterFunByOperandType: Map<IrClassifierSymbol, IrSimpleFunctionSymbol>
    abstract val ieee754equalsFunByOperandType: Map<IrClassifierSymbol, IrSimpleFunctionSymbol>
    abstract val booleanNotSymbol: IrSimpleFunctionSymbol
    abstract val eqeqeqSymbol: IrSimpleFunctionSymbol
    abstract val eqeqSymbol: IrSimpleFunctionSymbol
    abstract val throwCceSymbol: IrSimpleFunctionSymbol
    abstract val throwIseSymbol: IrSimpleFunctionSymbol
    abstract val andandSymbol: IrSimpleFunctionSymbol
    abstract val ororSymbol: IrSimpleFunctionSymbol
    abstract val noWhenBranchMatchedExceptionSymbol: IrSimpleFunctionSymbol
    abstract val illegalArgumentExceptionSymbol: IrSimpleFunctionSymbol
    abstract val checkNotNullSymbol: IrSimpleFunctionSymbol
    abstract val dataClassArrayMemberHashCodeSymbol: IrSimpleFunctionSymbol
    abstract val dataClassArrayMemberToStringSymbol: IrSimpleFunctionSymbol
    abstract val enumClass: IrClassSymbol

    abstract val intPlusSymbol: IrSimpleFunctionSymbol
    abstract val intTimesSymbol: IrSimpleFunctionSymbol
    abstract val intXorSymbol: IrSimpleFunctionSymbol

    abstract val extensionToString: IrSimpleFunctionSymbol
    abstract val memberToString: IrSimpleFunctionSymbol

    abstract val extensionStringPlus: IrSimpleFunctionSymbol
    abstract val memberStringPlus: IrSimpleFunctionSymbol

    abstract val arrayOf: IrSimpleFunctionSymbol
    abstract val arrayOfNulls: IrSimpleFunctionSymbol

    abstract val linkageErrorSymbol: IrSimpleFunctionSymbol

    abstract fun functionN(arity: Int): IrClass
    abstract fun kFunctionN(arity: Int): IrClass
    abstract fun suspendFunctionN(arity: Int): IrClass
    abstract fun kSuspendFunctionN(arity: Int): IrClass

    abstract fun getKPropertyClass(mutable: Boolean, n: Int): IrClassSymbol

    abstract fun getNonBuiltInFunctionsByExtensionReceiver(
        name: Name, vararg packageNameSegments: String
    ): Map<IrClassifierSymbol, IrSimpleFunctionSymbol>

    abstract fun getNonBuiltinFunctionsByReturnType(
        name: Name, vararg packageNameSegments: String
    ): Map<IrClassifierSymbol, IrSimpleFunctionSymbol>

    abstract fun getBinaryOperator(name: Name, lhsType: IrType, rhsType: IrType): IrSimpleFunctionSymbol
    abstract fun getUnaryOperator(name: Name, receiverType: IrType): IrSimpleFunctionSymbol

    abstract val operatorsPackageFragment: IrExternalPackageFragment
    abstract val kotlinInternalPackageFragment: IrExternalPackageFragment

    protected fun createIntrinsicConstEvaluationClass(): IrClass {
        return irFactory.buildClass {
            name = StandardClassIds.Annotations.IntrinsicConstEvaluation.shortClassName
            kind = ClassKind.ANNOTATION_CLASS
            modality = Modality.FINAL
        }.apply {
            parent = kotlinInternalPackageFragment
            createThisReceiverParameter()
            addConstructor { isPrimary = true }
            addFakeOverrides(IrTypeSystemContextImpl(this@IrBuiltIns))
        }
    }

    companion object {
        val KOTLIN_INTERNAL_IR_FQN = FqName("kotlin.internal.ir")
        val BUILTIN_OPERATOR = IrDeclarationOriginImpl("OPERATOR")
    }
}

object BuiltInOperatorNames {
    const val LESS = "less"
    const val LESS_OR_EQUAL = "lessOrEqual"
    const val GREATER = "greater"
    const val GREATER_OR_EQUAL = "greaterOrEqual"
    const val COMPARE_TO = "compareTo"
    const val EQEQ = "EQEQ"
    const val EQEQEQ = "EQEQEQ"
    const val IEEE754_EQUALS = "ieee754equals"
    const val THROW_CCE = "THROW_CCE"
    const val THROW_ISE = "THROW_ISE"
    const val NO_WHEN_BRANCH_MATCHED_EXCEPTION = "noWhenBranchMatchedException"
    const val ILLEGAL_ARGUMENT_EXCEPTION = "illegalArgumentException"
    const val ANDAND = "ANDAND"
    const val OROR = "OROR"
    const val CHECK_NOT_NULL = "CHECK_NOT_NULL"
}

@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
@Target(AnnotationTarget.CLASS)
annotation class InternalSymbolFinderAPI

@InternalSymbolFinderAPI
abstract class SymbolFinder {
    // TODO: drop variants from segments, add helper from whole fqn
    abstract fun findFunctions(callableId: CallableId): Iterable<IrSimpleFunctionSymbol>
    abstract fun findProperties(callableId: CallableId): Iterable<IrPropertySymbol>
    abstract fun findClass(classId: ClassId): IrClassSymbol?

    // TODO: replace this with lazy get
    abstract fun findGetter(property: IrPropertySymbol): IrSimpleFunctionSymbol?

    // TODO: replace this with get by CallableId
    abstract fun findBuiltInClassMemberFunctions(builtInClass: IrClassSymbol, name: Name): Iterable<IrSimpleFunctionSymbol>

    fun findFunctions(name: Name, vararg packageNameSegments: String = arrayOf("kotlin")): Iterable<IrSimpleFunctionSymbol> {
        return findFunctions(CallableId(FqName.fromSegments(listOf(*packageNameSegments)), name))
    }

    fun findFunctions(name: Name, packageFqName: FqName): Iterable<IrSimpleFunctionSymbol> {
        return findFunctions(CallableId(packageFqName, name))
    }

    fun findProperties(name: Name, packageFqName: FqName): Iterable<IrPropertySymbol> {
        return findProperties(CallableId(packageFqName, name))
    }

    fun findClass(name: Name, vararg packageNameSegments: String = arrayOf("kotlin")): IrClassSymbol? {
        return findClass(ClassId(FqName.fromSegments(listOf(*packageNameSegments)), name))
    }

    fun findClass(name: Name, packageFqName: FqName): IrClassSymbol? {
        return findClass(ClassId(packageFqName, name))
    }

    fun topLevelClass(classId: ClassId): IrClassSymbol =
        findClass(classId.shortClassName, classId.packageFqName) ?: error("No class $classId found")

    fun topLevelClass(packageName: FqName, name: String): IrClassSymbol =
        findClass(Name.identifier(name), packageName) ?: error("No class ${packageName}.${name} found")

    fun topLevelProperty(packageName: FqName, name: String): IrPropertySymbol {
        val elements = findProperties(Name.identifier(name), packageName).toList()
        require(elements.isNotEmpty()) { "No property ${packageName}.$name found" }
        require(elements.size == 1) {
            "Several properties ${packageName}.$name found:\n${elements.joinToString("\n")}"
        }
        return elements.single()
    }

    fun topLevelFunctions(packageName: FqName, name: String): Iterable<IrSimpleFunctionSymbol> =
        findFunctions(Name.identifier(name), packageName)

    inline fun topLevelFunction(
        callableId: CallableId,
        condition: (IrFunctionSymbol) -> Boolean = { true },
    ): IrSimpleFunctionSymbol {
        val elements = findFunctions(callableId).filter(condition)
        require(elements.isNotEmpty()) { "No function ${callableId} found corresponding given condition" }
        require(elements.size == 1) {
            "Several functions ${callableId} found corresponding given condition:\n${elements.joinToString("\n")}"
        }
        return elements.single()
    }

    inline fun topLevelFunction(
        packageName: FqName,
        name: String,
        condition: (IrFunctionSymbol) -> Boolean = { true },
    ): IrSimpleFunctionSymbol {
        return topLevelFunction(CallableId(packageName, Name.identifier(name)), condition)
    }

    fun findTopLevelPropertyGetter(packageName: FqName, name: String) =
        findGetter(topLevelProperty(packageName, name))
            ?: irError("Cannot find getter for $packageName.$name")
}
