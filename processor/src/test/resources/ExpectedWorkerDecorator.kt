package com.octo.workerdecorator

import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.processor.test.fixture.JavaInterface
import com.octo.workerdecorator.processor.test.fixture.JavaInterfaceDecorated
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinInterfaceDecorated
import java.lang.RuntimeException
import java.util.concurrent.Executor
import kotlin.Suppress

object WorkerDecorator {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified DECORATED> decorate(executor: Executor): WorkerDecoration<DECORATED> = when(DECORATED::class) {
        JavaInterface::class -> JavaInterfaceDecorated(executor) as WorkerDecoration<DECORATED>
        else ->
                throw RuntimeException("Impossible to find a decoration for ${DECORATED::class.java.name}")
    }

    fun decorate(implementation: KotlinInterface, executor: Executor): KotlinInterface = KotlinInterfaceDecorated(implementation, executor)
}
