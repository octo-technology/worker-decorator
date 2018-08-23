package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation.COROUTINES
import com.octo.workerdecorator.processor.entity.Language.KOTLIN

@Suppress("unused")
class GlobalConfiguration {
    companion object {
        @JvmField val LANGUAGE = KOTLIN
        @JvmField val IMPLEMENTATION = COROUTINES
    }
}