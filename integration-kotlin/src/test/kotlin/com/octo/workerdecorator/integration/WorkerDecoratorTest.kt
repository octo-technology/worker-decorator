package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.WorkerDecoration
import com.octo.workerdecorator.annotation.WorkerDecorator.decorate
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import java.util.concurrent.Executors

class WorkerDecoratorTest {

    @Test
    fun `returns a immutable decoration with the helper`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated = decorate<KotlinSimpleInterface>(Implementation(), executor)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterfaceDecorated::class.java)
        assertThat(decorated).isInstanceOf(KotlinSimpleInterface::class.java)
    }

    @Test
    fun `returns a mutable decoration with the helper`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated = decorate<KotlinSimpleInterface2>(executor)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterface2Decorated::class.java)
        assertThat(decorated).isInstanceOf(KotlinSimpleInterface2::class.java)
        assertThat(decorated).isInstanceOf(WorkerDecoration::class.java)
    }

    inner class Implementation : KotlinSimpleInterface {
        override fun a(param: Int) {
            // stuff
        }

        override fun b(param: Date) {
            // stuff
        }

    }
}