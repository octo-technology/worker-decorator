package com.octo.workerdecorator

import com.octo.workerdecorator.annotation.WeakWorkerDecoration
import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterfaceDecorated
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinInterfaceDecorated
import com.octo.workerdecorator.processor.test.fixture.KotlinParentInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinParentInterfaceDecorated
import java.lang.RuntimeException
import java.util.concurrent.Executor
import kotlin.Suppress

object WorkerDecorator {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified DECORATED> decorate(executor: Executor): WorkerDecoration<DECORATED> = when(DECORATED::class) {
        KotlinChildrenInterface::class ->
                KotlinChildrenInterfaceDecorated(executor) as WorkerDecoration<DECORATED>
        else ->
                throw RuntimeException("Impossible to find a decoration for ${DECORATED::class.java.name}")
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified DECORATED> decorateWeakly(executor: Executor): WeakWorkerDecoration<DECORATED> = when(DECORATED::class) {
        KotlinParentInterface::class ->
                KotlinParentInterfaceDecorated(executor) as WeakWorkerDecoration<DECORATED>
        else ->
                throw RuntimeException("Impossible to find a weak decoration for ${DECORATED::class.java.name}")
    }

    fun decorate(implementation: KotlinInterface, executor: Executor): KotlinInterface = KotlinInterfaceDecorated(implementation, executor)
}
