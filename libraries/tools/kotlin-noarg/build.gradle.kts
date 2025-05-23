plugins {
    id("gradle-plugin-common-configuration")
}

dependencies {
    commonApi(platform(project(":kotlin-gradle-plugins-bom")))
    commonApi(project(":kotlin-gradle-plugin-model"))

    testImplementation(gradleApi())
    testImplementation(libs.junit4)
}

gradlePlugin {
    plugins {
        create("kotlinNoargPlugin") {
            id = "org.jetbrains.kotlin.plugin.noarg"
            displayName = "Kotlin No Arg compiler plugin"
            description = displayName
            implementationClass = "org.jetbrains.kotlin.noarg.gradle.NoArgGradleSubplugin"
        }
        create("kotlinJpaPlugin") {
            id = "org.jetbrains.kotlin.plugin.jpa"
            displayName = "Kotlin JPA compiler plugin"
            description = displayName
            implementationClass = "org.jetbrains.kotlin.noarg.gradle.KotlinJpaSubplugin"
        }
    }
}
