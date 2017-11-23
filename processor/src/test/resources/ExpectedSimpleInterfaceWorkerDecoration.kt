package com.octo.workerdecorator.processor.test.fixture

import java.util.Currency
import java.util.Date
import java.util.concurrent.Executor

class KotlinInterfaceDecorated(private val executor: Executor, private val decorated: KotlinInterface) : KotlinInterface {
  override fun pam() {
    executor.execute { decorated.pam() }
  }

  override fun jim(arg0: Currency, arg1: Date?) {
    executor.execute { decorated.jim(arg0, arg1) }
  }
}
