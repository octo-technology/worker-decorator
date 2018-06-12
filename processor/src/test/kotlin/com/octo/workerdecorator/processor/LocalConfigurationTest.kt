package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LocalConfigurationTest {

    @Test
    fun `returns the correct Kotlin setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.language

        assertThat(language).isEqualTo(KOTLIN)
    }

    @Test
    fun `returns the correct Executor setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.implementation

        assertThat(language).isEqualTo(EXECUTOR)
    }
}