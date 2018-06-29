package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.JAVA

@Suppress("unused")
class LocalConfiguration {
    companion object {
        @JvmField val LANGUAGE = JAVA
        @JvmField val IMPLEMENTATION = EXECUTOR
    }
}