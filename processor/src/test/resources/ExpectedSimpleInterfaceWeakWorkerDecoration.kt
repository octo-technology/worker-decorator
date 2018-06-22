package com.octo.workerdecorator.processor.test.fixture

import java.lang.ref.WeakReference
import java.util.Date
import java.util.concurrent.Executor
import kotlin.String

class KotlinInterfaceDecorated(decorated: KotlinInterface, private val executor: Executor) : KotlinInterface {
    private val decorated: WeakReference<KotlinInterface> = WeakReference(decorated)

    override fun pam() {
        executor.execute { decorated.get()?.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        executor.execute { decorated.get()?.jim(arg0, arg1) }
    }
}
