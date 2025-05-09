// Auto-generated by GenerateSteppedRangesCodegenTestData. Do not edit!
// WITH_STDLIB
// LANGUAGE: +RangeUntilOperator
@file:OptIn(ExperimentalStdlibApi::class)
import kotlin.test.*

fun box(): String {
    val uintList = mutableListOf<UInt>()
    for (i in 1u..<2u step 2) {
        uintList += i
    }
    assertEquals(listOf(1u), uintList)

    val ulongList = mutableListOf<ULong>()
    for (i in 1uL..<2uL step 2L) {
        ulongList += i
    }
    assertEquals(listOf(1uL), ulongList)

    return "OK"
}