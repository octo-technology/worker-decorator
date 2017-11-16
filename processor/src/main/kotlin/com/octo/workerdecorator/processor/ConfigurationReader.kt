package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE

class ConfigurationReader {

    fun read(): Configuration {
        // This is "mocked" for now
        return Configuration(KOTLIN, EXECUTOR, UNMUTABLE)
    }
}