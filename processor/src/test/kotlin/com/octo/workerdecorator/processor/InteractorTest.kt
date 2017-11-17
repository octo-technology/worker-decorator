package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Document
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.lang.model.element.TypeElement

@RunWith(MockitoJUnitRunner::class)
class InteractorTest {

    @Mock lateinit var analyser: Analyser
    @Mock lateinit var configurationReader: ConfigurationReader
    @Mock lateinit var generatorFactory: GeneratorFactory
    @Mock lateinit var sourceWriterFactory: SourceWriterFactory
    @InjectMocks lateinit var interactor: Interactor

    @Test
    fun `interactor orchestrates correctly`() {
        // Given
        val element: TypeElement = mock()
        val annotation: Decorate = mock()
        val document: Document = mock()
        val configuration: Configuration = mock()
        val generator: Generator = mock()
        val sourceWriter: SourceWriter = mock()
        val source = "beautiful source code"

        given(analyser.analyse(element))
                .willReturn(document)
        given(configurationReader.read(annotation))
                .willReturn(configuration)
        given(generatorFactory.make(configuration))
                .willReturn(generator)
        given(sourceWriterFactory.make(configuration))
                .willReturn(sourceWriter)
        given(generator.generate(document))
                .willReturn(source)

        // When
        interactor.process(element, annotation)

        // Then
        then(sourceWriter).should().write(document, "beautiful source code")
    }
}