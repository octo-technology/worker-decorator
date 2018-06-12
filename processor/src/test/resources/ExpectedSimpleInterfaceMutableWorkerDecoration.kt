package com.octo.workerdecorator.processor.test.fixture

import com.octo.workerdecorator.annotation.WorkerDecoration
import java.util.Date
import java.util.concurrent.Executor
import kotlin.String

class KotlinInterfaceDecorated(private val executor: Executor) : KotlinInterface,
        WorkerDecoration<KotlinInterface> {
    override var decorated: KotlinInterface? = null

    override fun pam() {
        executor.execute { decorated?.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        executor.execute { decorated?.jim(arg0, arg1) }
    }

    override fun decoration(): KotlinInterface = this
}
