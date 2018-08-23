package com.octo.workerdecorator

import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinInterfaceDecorated
import kotlin.coroutines.experimental.CoroutineContext

object WorkerDecorator {
    fun decorate(implementation: KotlinInterface, coroutineContext: CoroutineContext): KotlinInterface = KotlinInterfaceDecorated(implementation, coroutineContext)
}
