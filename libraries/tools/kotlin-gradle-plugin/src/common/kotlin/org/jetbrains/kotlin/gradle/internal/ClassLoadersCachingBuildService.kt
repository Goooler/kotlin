/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.internal

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.tasks.Internal
import org.jetbrains.kotlin.gradle.tasks.withType
import org.jetbrains.kotlin.gradle.utils.SingleActionPerProject
import org.jetbrains.kotlin.gradle.utils.registerClassLoaderScopedBuildService
import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.ConcurrentHashMap

internal interface UsesClassLoadersCachingBuildService : Task {
    @get:Internal
    val classLoadersCachingService: Property<ClassLoadersCachingBuildService>
}

/**
 * A [BuildService] for caching [ClassLoader] instances
 */
internal abstract class ClassLoadersCachingBuildService : BuildService<BuildServiceParameters.None> {
    private val logger = Logging.getLogger(javaClass)

    fun getClassLoader(
        classpath: List<File>,
        parentClassLoaderProvider: ParentClassLoaderProvider = DefaultParentClassLoaderProvider()
    ): ClassLoader {
        return classLoaders.computeIfAbsent(ClassLoaderCacheKey(classpath, parentClassLoaderProvider)) {
            logger.debug("Creating a new classloader for classpath $classpath")
            URLClassLoader(classpath.map { it.toURI().toURL() }.toTypedArray(), parentClassLoaderProvider.getClassLoader())
        }
    }

    companion object {
        // The service could be used by multiple tasks in parallel, so the map have to be synchronized
        private val classLoaders = ConcurrentHashMap<ClassLoaderCacheKey, ClassLoader>()

        fun registerIfAbsent(project: Project) =
            project.gradle.registerClassLoaderScopedBuildService(ClassLoadersCachingBuildService::class).also { serviceProvider ->
                SingleActionPerProject.run(project, UsesClassLoadersCachingBuildService::class.java.name) {
                    project.tasks.withType<UsesClassLoadersCachingBuildService>().configureEach { task ->
                        task.usesService(serviceProvider)
                        task.classLoadersCachingService.value(serviceProvider).disallowChanges()
                    }
                }
            }
    }
}

private data class ClassLoaderCacheKey(
    val classpath: List<File>,
    val parentClassLoaderProvider: ParentClassLoaderProvider,
)

/**
 * A provider of the parent [ClassLoader] for a newly created ClassLoader instances.
 *
 * An implementation must override `equals` and `hashCode`! It's used as a part of a [Map] key
 */
internal fun interface ParentClassLoaderProvider {
    fun getClassLoader(): ClassLoader?
}

private class DefaultParentClassLoaderProvider : ParentClassLoaderProvider {
    override fun getClassLoader(): ClassLoader = javaClass.classLoader

    override fun hashCode() = javaClass.hashCode()

    override fun equals(other: Any?) = other is DefaultParentClassLoaderProvider && other.javaClass == javaClass
}