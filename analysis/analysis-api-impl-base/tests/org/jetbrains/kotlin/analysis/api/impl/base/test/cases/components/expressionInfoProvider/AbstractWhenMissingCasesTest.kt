/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.expressionInfoProvider

import org.jetbrains.kotlin.analysis.test.framework.base.AbstractAnalysisApiBasedTest
import org.jetbrains.kotlin.analysis.test.framework.projectStructure.KtTestModule
import org.jetbrains.kotlin.analysis.test.framework.services.expressionMarkerProvider
import org.jetbrains.kotlin.analysis.test.framework.utils.executeOnPooledThreadInReadAction
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.test.services.TestServices
import org.jetbrains.kotlin.test.services.assertions

abstract class AbstractWhenMissingCasesTest : AbstractAnalysisApiBasedTest() {
    override fun doTestByMainFile(mainFile: KtFile, mainModule: KtTestModule, testServices: TestServices) {
        val whenExpression = testServices.expressionMarkerProvider.getBottommostElementOfTypeAtCaret<KtWhenExpression>(mainFile)

        val actual = executeOnPooledThreadInReadAction {
            copyAwareAnalyzeForTest(whenExpression) { contextWhenExpression ->
                buildString {
                    for (missingCase in contextWhenExpression.computeMissingCases()) {
                        appendLine(missingCase::class.simpleName + " - " + missingCase.branchConditionText)
                    }
                }
            }
        }

        testServices.assertions.assertEqualsToTestOutputFile(actual)
    }
}
