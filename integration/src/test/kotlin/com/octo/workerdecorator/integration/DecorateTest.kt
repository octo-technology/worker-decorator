package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.KotlinDecoration.decorate
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import java.util.concurrent.Executors

class DecorateTest {

    @Test
    fun `decorates with the helper the given implementation`() {
        val executor = Executors.newSingleThreadScheduledExecutor()
        val decorated = decorate<KotlinSimpleInterface>(Implementation(), executor)

        assertThat(decorated).isOfAnyClassIn(KotlinSimpleInterfaceDecorated::class.java)
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