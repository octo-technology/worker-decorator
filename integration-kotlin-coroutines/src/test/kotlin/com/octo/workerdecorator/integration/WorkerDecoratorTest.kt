package com.octo.workerdecorator.integration

import com.octo.workderdecorator.integration.KotlinSimpleInterface
import com.octo.workderdecorator.integration.KotlinSimpleInterfaceDecorated
import com.octo.workerdecorator.WorkerDecorator
import kotlinx.coroutines.experimental.CommonPool
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * Very weak tests just to check that the decorations and helpers are generated and that it all compiles
 */
class WorkerDecoratorTest {

    @Test
    fun `returns a coroutines decoration with the helper`() {
        val coroutineContext = CommonPool
        val decorated = WorkerDecorator.decorate(Implementation(), coroutineContext)

        assertThat(decorated).isInstanceOf(KotlinSimpleInterfaceDecorated::class.java)
        assertThat(decorated).isInstanceOf(KotlinSimpleInterface::class.java)
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