package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation
import com.octo.workerdecorator.processor.entity.Language

@Suppress("unused")
class LocalConfiguration {
    companion object {
        @JvmField val LANGUAGE = Language.JAVA
        @JvmField val IMPLEMENTATION = Implementation.EXECUTOR
    }
}