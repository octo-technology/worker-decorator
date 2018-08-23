package com.octo.workerdecorator.processor.test.fixture

import java.util.Date
import kotlin.String
import kotlin.coroutines.experimental.CoroutineContext
import kotlinx.coroutines.experimental.launch

class KotlinInterfaceDecorated(private val decorated: KotlinInterface, private val coroutineContext: CoroutineContext) : KotlinInterface {
    override fun pam() {
        launch(coroutineContext) { decorated.pam() }
    }

    override fun jim(arg0: String, arg1: Date?) {
        launch(coroutineContext) { decorated.jim(arg0, arg1) }
    }
}
