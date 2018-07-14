package com.octo.workerdecorator.processor.test.fixture

import kotlinx.coroutines.experimental.launch
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext

class KotlinInterfaceDecorated(private val decorated: KotlinInterface, private val coroutineContext: CoroutineContext) : KotlinInterface {
    override fun pam() {
        launch(coroutineContext) { decorated.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        launch(coroutineContext) { decorated.jim(arg0, arg1) }
    }
}
