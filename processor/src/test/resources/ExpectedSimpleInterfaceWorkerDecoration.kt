package com.octo.workerdecorator.processor.test.fixture

import java.util.concurrent.Executor
import kotlin.Int
import kotlin.String

class SimpleInterfaceDecorated(private val executor: Executor, private val decorated: SimpleInterface) : SimpleInterface {
  override fun pam() {
    executor.execute { decorated.pam() }
  }

  override fun jim(arg0: Int, arg1: String) {
    executor.execute { decorated.jim(arg0, arg1) }
  }
}
