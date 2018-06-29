package com.octo.workerdecorator.processor.test.fixture

import com.octo.workerdecorator.WorkerDecoration
import java.lang.ref.WeakReference
import java.util.Date
import java.util.concurrent.Executor
import kotlin.String

class KotlinInterfaceDecorated(private val executor: Executor) : KotlinInterface,
        WorkerDecoration<KotlinInterface> {
    private var decorated: WeakReference<KotlinInterface?>? = null

    override fun pam() {
        executor.execute { decorated?.get()?.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        executor.execute { decorated?.get()?.jim(arg0, arg1) }
    }

    override fun setDecorated(decorated: KotlinInterface?) {
        this.decorated = WeakReference(decorated)
    }

    override fun asType(): KotlinInterface = this
}
