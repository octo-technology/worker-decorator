package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.JAVA
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GlobalConfigurationTest {

    @Test
    fun `returns the correct JAVA setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.language

        assertThat(language).isEqualTo(JAVA)
    }

    @Test
    fun `returns the correct EXECUTOR setting`() {
        val configuration = ReflectConfigurationReader()
        val language = configuration.implementation

        assertThat(language).isEqualTo(EXECUTOR)
    }
}