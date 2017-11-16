package com.octo.workerdecorator.processor

import com.google.testing.compile.CompilationRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class IntegrationTests {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    @Test
    fun `project boot test`() {
        // Given
        val configurationReader: ConfigurationReader = mock()
        val configuration = Configuration(KOTLIN, EXECUTOR, UNMUTABLE)
        given(configurationReader.read()).willReturn(configuration)

        val analyser = Analyser(compilationRule.elements)
        val generatorFactory = GeneratorFactory()
        val sourceWriter = SourceWriter()

        val input = compilationRule.elements.getTypeElement(SimpleInterface::class.qualifiedName)

        val interactor = Interactor(analyser, configurationReader, generatorFactory, sourceWriter)

        // When
        interactor.process(input)

        // Then
        // TODO Compare the output with the ExpectedSimpleInterfaceWorkerDecoration.kt file
    }
}