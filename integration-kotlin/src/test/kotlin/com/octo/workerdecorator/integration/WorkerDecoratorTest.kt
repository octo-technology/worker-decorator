package com.octo.workerdecorator.integration

import com.octo.workerdecorator.WorkerDecorator
import com.octo.workerdecorator.annotation.WorkerDecoration
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import java.util.concurrent.Executors

class WorkerDecoratorTest {

    @Test
    fun `returns a immutable decoration with the helper`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated = WorkerDecorator.decorate(Implementation(), executor)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterfaceDecorated::class.java)
        assertThat(decorated).isInstanceOf(KotlinSimpleInterface::class.java)
    }

    @Test
    fun `returns a immutable weak decoration with the helper`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated = WorkerDecorator.decorate(Implementation3(), executor)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterface3Decorated::class.java)
        assertThat(decorated).isInstanceOf(KotlinSimpleInterface3::class.java)
    }

    @Test
    fun `returns a mutable decoration with the helper`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated: WorkerDecoration<KotlinSimpleInterface2> = WorkerDecorator.decorate(executor)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterface2Decorated::class.java)
    }

    inner class Implementation : KotlinSimpleInterface {
        override fun a(param: Int) {
            // stuff
        }

        override fun b(param: Date) {
            // stuff
        }
    }

    inner class Implementation3 : KotlinSimpleInterface3 {
        override fun a(param: Int) {
            // stuff
        }

        override fun b(param: Double) {
            // stuff
        }
    }
}