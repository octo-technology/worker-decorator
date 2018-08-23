package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation.COROUTINES
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GlobalConfigurationTest {

    @Test
    fun `returns the correct KOTLIN setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.language

        assertThat(language).isEqualTo(KOTLIN)
    }

    @Test
    fun `returns the correct COROUTINES setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.implementation

        assertThat(language).isEqualTo(COROUTINES)
    }
}