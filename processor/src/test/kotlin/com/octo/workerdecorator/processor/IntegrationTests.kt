package com.octo.workerdecorator.processor

import com.octo.workerdecorator.processor.test.fixture.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.SimpleInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class IntegrationTests : CompilationAwareTest() {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `project boot test`() {
        // Given
        val folder = testFolder.newFolder()!!

        val analyser = Analyser(compilationRule.elements)
        val configurationReader = ConfigurationReader()
        val generatorFactory = GeneratorFactory()
        val sourceWriter = SourceWriter(folder)
        val input = typeElement(SimpleInterface::class)

        val interactor = Interactor(analyser, configurationReader, generatorFactory, sourceWriter)

        // When
        interactor.process(input)

        // Then
        val resultingContent = File(folder, "SimpleInterfaceDecorated.kt").readText()
        val targetContent = readResource("ExpectedSimpleInterfaceWorkerDecoration.kt")

        assertThat(resultingContent).isEqualTo(targetContent)
    }

    private fun readResource(file: String)
            = javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}