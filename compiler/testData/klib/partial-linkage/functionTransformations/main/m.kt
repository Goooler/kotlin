@file:Suppress("RemoveRedundantSpreadOperator")

import abitestutils.abiTest

fun box() = abiTest {
    val ii: InterfaceImpl = InterfaceImpl()
    val i: Interface = ii
    val aci: AbstractClassImpl = AbstractClassImpl()
    val ac: AbstractClass = aci
    val oci: OpenClassImpl = OpenClassImpl()
    val oc: OpenClass = oci
    val soc: StableOpenClass = StableOpenClass()
    val sci: StableClassImpl = StableClassImpl()
    val sci2: StableClassImpl2 = StableClassImpl2()

    expectSuccess("memberOperatorsToNonOperators: a=Alice,b=Bob") { memberOperatorsToNonOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("extensionOperatorsToNonOperators: a=Alice,b=Bob") { extensionOperatorsToNonOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("memberNonOperatorsToOperators: a=Alice,b=Bob") { memberNonOperatorsToOperators("a" to "Alice", "b" to "Bob") }
    expectSuccess("extensionNonOperatorsToOperators: a=Alice,b=Bob") { extensionNonOperatorsToOperators("a" to "Alice", "b" to "Bob") }

    expectSuccess(3) { memberNonInfixToInfix(1, 2) }
    expectSuccess(3) { extensionNonInfixToInfix(1, 2) }
    expectSuccess(3) { memberInfixToNonInfix(1, 2) }
    expectSuccess(3) { extensionInfixToNonInfix(1, 2) }

    expectSuccess(6) { nonTailrecToTailrec(3) }
    expectSuccess(6) { tailrecToNonTailrec(3) }

    expectSuccess(142) { firstDefaultValueInFunctionInStableOpenClass(soc, 100) }
    expectSuccess(142) { lastDefaultValueInFunctionInStableOpenClass(soc, 100) }
    expectSuccess(142) { firstDefaultValueInFunctionInStableClassImpl(sci, 100) }
    expectSuccess(142) { lastDefaultValueInFunctionInStableClassImpl(sci, 100) }
    expectSuccess(-58) { firstDefaultValueInFunctionInStableClassImpl2(sci2, 100) }
    expectSuccess(58) { lastDefaultValueInFunctionInStableClassImpl2(sci2, 100) }

    expectFailure(linkage("Function 'removedFirstDefaultValue' can not be called: The function has some value parameters for which neither the call site provides an argument, nor do they have a default value: a")) { removedFirstDefaultValueInFunction(1) }
    expectSuccess(100) { removedVarargFirstDefaultValueInFunction(100) } // Default IntArray value disappears. So it contrinutes 0 to the sum.
    expectFailure(linkage("Function 'removedLastDefaultValue' can not be called: The function has some value parameters for which neither the call site provides an argument, nor do they have a default value: b")) { removedLastDefaultValueInFunction(1) }
    expectSuccess(100) { removedVarargLastDefaultValueInFunction(100) } // Default IntArray value disappears. So it contrinutes 0 to the sum.
    expectFailure(linkage("Constructor 'RemovedFirstDefaultValueInConstructor.<init>' can not be called: The constructor has some value parameters for which neither the call site provides an argument, nor do they have a default value: a")) { removedFirstDefaultValueInConstructor(1) }
    expectFailure(linkage("Constructor 'RemovedLastDefaultValueInConstructor.<init>' can not be called: The constructor has some value parameters for which neither the call site provides an argument, nor do they have a default value: b")) { removedLastDefaultValueInConstructor(1) }

    expectSuccess(0) { singleVarargArgument() }
    expectSuccess(1) { singleVarargArgument(1) }
    expectSuccess(3) { singleVarargArgument(1, 2) }
    expectSuccess(1) { singleVarargArgument(*intArrayOf(1)) }
    expectSuccess(3) { singleVarargArgument(*intArrayOf(1, 2)) }
    expectSuccess(-6) { singleVarargArgumentWithDefaultValue() }
    expectSuccess(1) { singleVarargArgumentWithDefaultValue(1) }
    expectSuccess(3) { singleVarargArgumentWithDefaultValue(1, 2) }
    expectSuccess(1) { singleVarargArgumentWithDefaultValue(*intArrayOf(1)) }
    expectSuccess(3) { singleVarargArgumentWithDefaultValue(*intArrayOf(1, 2)) }
    expectSuccess(110) { varargArgumentAndOtherArguments(100, last = 10) }
    expectSuccess(111) { varargArgumentAndOtherArguments(100, 1, last = 10) }
    expectSuccess(113) { varargArgumentAndOtherArguments(100, 1, 2, last = 10) }
    expectSuccess(111) { varargArgumentAndOtherArguments(100, *intArrayOf(1), last = 10) }
    expectSuccess(113) { varargArgumentAndOtherArguments(100, *intArrayOf(1, 2), last = 10) }
    expectSuccess(-110) { varargArgumentAndOtherArgumentsWithDefaultValues() }
    expectSuccess(90) { varargArgumentAndOtherArgumentsWithDefaultValues(100) }
    expectSuccess(110) { varargArgumentAndOtherArgumentsWithDefaultValues(100, last = 10) }
    expectSuccess(91) { varargArgumentAndOtherArgumentsWithDefaultValues(100, 1) }
    expectSuccess(93) { varargArgumentAndOtherArgumentsWithDefaultValues(100, 1, 2) }
    expectSuccess(113) { varargArgumentAndOtherArgumentsWithDefaultValues(100, 1, 2, last = 10) }
    expectSuccess(-109) { @Suppress("REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION") varargArgumentAndOtherArgumentsWithDefaultValues(elements = *intArrayOf(1)) }
    expectSuccess(-107) { @Suppress("REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION") varargArgumentAndOtherArgumentsWithDefaultValues(elements = *intArrayOf(1, 2)) }
    expectSuccess(104) { varargArgumentWithDefaultValueAndOtherArguments(100, last = 10) }
    expectSuccess(111) { varargArgumentWithDefaultValueAndOtherArguments(100, 1, last = 10) }
    expectSuccess(113) { varargArgumentWithDefaultValueAndOtherArguments(100, 1, 2, last = 10) }
    expectSuccess(111) { varargArgumentWithDefaultValueAndOtherArguments(100, *intArrayOf(1), last = 10) }
    expectSuccess(113) { varargArgumentWithDefaultValueAndOtherArguments(100, *intArrayOf(1, 2), last = 10) }
    expectSuccess(-116) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues() }
    expectSuccess(84) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(100) }
    expectSuccess(104) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(100, last = 10) }
    expectSuccess(91) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(100, 1) }
    expectSuccess(93) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(100, 1, 2) }
    expectSuccess(113) { varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(100, 1, 2, last = 10) }
    expectSuccess(-109) { @Suppress("REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION") varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(elements = *intArrayOf(1)) }
    expectSuccess(-107) { @Suppress("REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION") varargArgumentWithDefaultValueAndOtherArgumentsWithDefaultValues(elements = *intArrayOf(1, 2)) }

    expectSuccess(-1) { suspendToNonSuspendFunction1(1) }
    expectSuccess(-2) { suspendToNonSuspendFunction2(2) }
    expectFailure(linkage("Function 'nonSuspendToSuspendFunction' can not be called: Suspend function can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunction1(4) }
    expectSuccess(-5) { nonSuspendToSuspendFunction2(5) }

    expectFailure(linkage("Abstract function 'suspendToNonSuspendFunction' is not implemented in non-abstract class 'InterfaceImpl'")) { suspendToNonSuspendFunctionInInterface(i, 1) }
    expectFailure(linkage("Function 'nonSuspendToSuspendFunction' can not be called: Suspend function can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInInterface(i, 2) }
    expectSuccess("InterfaceImpl.suspendToNonSuspendFunction(3)") { suspendToNonSuspendFunctionInInterfaceImpl(ii, 3) }
    expectSuccess("InterfaceImpl.nonSuspendToSuspendFunction(4)") { nonSuspendToSuspendFunctionInInterfaceImpl(ii, 4) }

    expectFailure(linkage("Abstract function 'suspendToNonSuspendFunction' is not implemented in non-abstract class 'AbstractClassImpl'")) { suspendToNonSuspendFunctionInAbstractClass(ac, 5) }
    expectFailure(linkage("Function 'nonSuspendToSuspendFunction' can not be called: Suspend function can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInAbstractClass(ac, 6) }
    expectSuccess("AbstractClassImpl.suspendToNonSuspendFunction(7)") { suspendToNonSuspendFunctionInAbstractClassImpl(aci, 7) }
    expectSuccess("AbstractClassImpl.nonSuspendToSuspendFunction(8)") { nonSuspendToSuspendFunctionInAbstractClassImpl(aci, 8) }

    expectSuccess("OpenClassV2.suspendToNonSuspendFunction(9)") { suspendToNonSuspendFunctionInOpenClass(oc, 9) } // Function of the base class is called instead of overridden function in inherited class.
    expectFailure(linkage("Function 'nonSuspendToSuspendFunction' can not be called: Suspend function can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionInOpenClass(oc, 10) }
    expectSuccess("OpenClassImpl.suspendToNonSuspendFunction(11)") { suspendToNonSuspendFunctionInOpenClassImpl(oci, 11) }
    expectSuccess("OpenClassImpl.nonSuspendToSuspendFunction(12)") { nonSuspendToSuspendFunctionInOpenClassImpl(oci, 12) }
    expectSuccess("OpenClassV2.suspendToNonSuspendFunctionWithDelegation(13) called from OpenClassImpl.suspendToNonSuspendFunctionWithDelegation(13)") { suspendToNonSuspendFunctionWithDelegation(oci, 13) }
    expectFailure(linkage("Function 'nonSuspendToSuspendFunctionWithDelegation' can not be called: Suspend function can be called only from a coroutine or another suspend function")) { nonSuspendToSuspendFunctionWithDelegation(oci, 14) }

    expectSuccess("success") { nonLocalReturnFromArrayConstructorLambda("success", "failure") }
    expectSuccess(100) { nonLocalReturnFromIntArrayConstructorLambda(100, -100) }

    expectFailure(linkage("Function 'test' can not be called: No function found for symbol '/KT59153.test'")) {
        objectKT59153Delegate.test { "Hello!" }
    }
}
