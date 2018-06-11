package com.octo.workerdecorator.processor.test.fixture

import java.util.Date
import java.util.concurrent.Executor
import kotlin.String

class KotlinInterfaceDecorated(private val executor: Executor, private val decorated: KotlinInterface) : KotlinInterface {
    override fun pam() {
        executor.execute { decorated.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        executor.execute { decorated.jim(arg0, arg1) }
    }
}
