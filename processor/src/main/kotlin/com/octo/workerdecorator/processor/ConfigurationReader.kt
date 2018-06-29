package com.octo.workerdecorator.processor

import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.HelperConfiguration
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.entity.ReferenceStrength.WEAK

/**
 * Class responsible for reading the wanted options for decorations and helpers to generate
 */
class ConfigurationReader(private val globalConfiguration: GlobalConfigurationReader) {

    fun read(annotation: Decorate): Configuration {
        val language = globalConfiguration.language
        val implementation = globalConfiguration.implementation

        val mutability = when (annotation.mutable) {
            true -> MUTABLE
            false -> IMMUTABLE
        }

        val strength = when (annotation.weak) {
            true -> WEAK
            false -> STRONG
        }

        return Configuration(language, implementation, mutability, strength)
    }

    fun read(): HelperConfiguration =
        HelperConfiguration(globalConfiguration.language, globalConfiguration.implementation)

}