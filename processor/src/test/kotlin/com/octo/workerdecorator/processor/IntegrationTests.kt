package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Answers
import javax.annotation.processing.Filer

class IntegrationTests : CompilationAwareTest() {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `project boot test`() {
        // Given
        val file = testFolder.newFile()

        val analyser = Analyser(compilationRule.elements)
        val configurationReader = ConfigurationReader()
        val generatorFactory = GeneratorFactory()

        val filer: Filer = mock(defaultAnswer = Answers.RETURNS_DEEP_STUBS)
        val sourceWriter = SourceWriter(filer)
        given(filer.createSourceFile(any()).openWriter())
                .willReturn(file.writer())

        val input = typeElement(SimpleInterface::class)

        val interactor = Interactor(analyser, configurationReader, generatorFactory, sourceWriter)

        // When
        interactor.process(input)

        // Then
        val resultingContent = file.readText()
        val targetContent = readResource("ExpectedSimpleInterfaceWorkerDecoration.kt")

        assertThat(resultingContent).isEqualTo(targetContent)
    }

    private fun readResource(file: String)
            = javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}