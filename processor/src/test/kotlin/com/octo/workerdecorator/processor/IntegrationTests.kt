package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.UNMUTABLE
import com.octo.workerdecorator.processor.sourcewriter.KotlinSourceWriter
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
    fun `Kotlin executor unmutable`() {
        // Given
        val folder = testFolder.newFolder()

        val analyser = Analyser(compilationRule.elements)
        val generatorFactory = GeneratorFactory()
        val configurationReader: ConfigurationReader = mock()
        val sourceWriterFactory: SourceWriterFactory = mock()
        val input = typeElement(SimpleInterface::class)

        val configuration = Configuration(KOTLIN, EXECUTOR, UNMUTABLE)
        given(configurationReader.read()).willReturn(configuration)
        given(sourceWriterFactory.make(configuration)).willReturn(KotlinSourceWriter(folder))

        val interactor = Interactor(analyser, configurationReader, generatorFactory, sourceWriterFactory)

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