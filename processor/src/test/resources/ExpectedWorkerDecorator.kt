package com.octo.workerdecorator

import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.processor.test.fixture.JavaInterface
import com.octo.workerdecorator.processor.test.fixture.JavaInterfaceDecorated
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinInterfaceDecorated
import java.util.concurrent.Executor

object WorkerDecorator {
    fun decorate(executor: Executor): WorkerDecoration<KotlinInterface> = KotlinInterfaceDecorated(executor)

    fun decorate(implementation: JavaInterface, executor: Executor): JavaInterface = JavaInterfaceDecorated(implementation, executor)
}
