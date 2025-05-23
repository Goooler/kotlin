/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.scripting.compiler.plugin.extensions

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory.IrGeneratorExtensionMarkerForExpressionEvaluation
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.platform.jvm.isJvm
import org.jetbrains.kotlin.scripting.compiler.plugin.irLowerings.JvmSymbolsForScripting
import org.jetbrains.kotlin.scripting.compiler.plugin.irLowerings.ScriptsToClassesLowering

class ScriptLoweringExtension : IrGenerationExtension, IrGeneratorExtensionMarkerForExpressionEvaluation {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext,
    ) {
        val platform = pluginContext.platform
        when {
            platform.isJvm() -> {
                val symbolsForScripting = JvmSymbolsForScripting(pluginContext, moduleFragment)
                ScriptsToClassesLowering(pluginContext, symbolsForScripting).lower(moduleFragment)
            }
        }
    }
}