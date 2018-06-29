package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class ReflectConfigurationReader : GlobalConfigurationReader {
    companion object {
        const val CONFIGURATION_CLASS = "com.octo.workerdecorator.processor.GlobalConfiguration"
    }

    override val language: Language by lazy { readField("LANGUAGE", KOTLIN) }

    override val implementation: Implementation by lazy { readField("IMPLEMENTATION", EXECUTOR) }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> readField(name: String, default: T): T {
        return try {
            val clazz = javaClass.classLoader.loadClass(CONFIGURATION_CLASS)
            val fields = clazz.declaredFields

            if (fields.isEmpty()) throw NoSuchFieldException()

            val field = fields.asList().find { it.name == name } ?: throw NoSuchFieldException()
            val value = field.get(null)

            value as? T ?: throw IllegalArgumentException()

        } catch (e: ReflectiveOperationException) {
            default
        } catch (e: RuntimeException) {
            default
        }
    }
}