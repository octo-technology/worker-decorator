package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Implementation.EXECUTOR
import com.octo.workerdecorator.processor.entity.Language.KOTLIN
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.extension.children
import com.octo.workerdecorator.processor.sourcewriter.KotlinSourceWriter
import com.octo.workerdecorator.processor.test.CompilationAwareTest
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class IntegrationTests : CompilationAwareTest() {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Test
    fun `Kotlin executor immutable`() {
        // Given
        val folder = testFolder.newFolder()

        val input = typeElement(KotlinInterface::class)
        val annotation: Decorate = mock()

        val analyser = Analyser(compilationRule.elements)
        val generatorFactory = GeneratorFactory()
        val configurationMaker: ConfigurationMaker = mock()
        val sourceWriterFactory: SourceWriterFactory = mock()

        val configuration = Configuration(KOTLIN, EXECUTOR, IMMUTABLE, STRONG)
        given(configurationMaker.read(annotation)).willReturn(configuration)
        given(sourceWriterFactory.make(configuration.language)).willReturn(KotlinSourceWriter(folder))

        val interactor = Interactor(analyser, configurationMaker, generatorFactory, sourceWriterFactory)

        // When
        interactor.process(input, annotation)

        // Then
        val resultingContent =
            folder.children("KotlinInterfaceDecorated.kt", "com.octo.workerdecorator.processor.test.fixture").readText()
        val targetContent = readResource("ExpectedSimpleInterfaceWorkerDecoration.kt")

        assertThat(resultingContent).isEqualTo(targetContent)
    }

    private fun readResource(file: String) =
        javaClass.classLoader.getResourceAsStream(file).bufferedReader().use { it.readText() }
}