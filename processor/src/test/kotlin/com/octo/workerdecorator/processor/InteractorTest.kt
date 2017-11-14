package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.octo.workerdecorator.processor.entity.Configuration
import com.octo.workerdecorator.processor.entity.Document
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.lang.model.element.Element

@RunWith(MockitoJUnitRunner::class)
class InteractorTest {

    @JvmField
    @Rule
    var testFolder = TemporaryFolder()

    @Mock lateinit var analyser: Analyser
    @Mock lateinit var configurationReader: ConfigurationReader
    @Mock lateinit var generatorFactory: GeneratorFactory
    @Mock lateinit var sourceWriter: SourceWriter
    @InjectMocks lateinit var interactor: Interactor

    @Test
    fun `interactor orchestrates correctly`() {
        // Given
        val element: Element = mock()
        val document: Document = mock()
        val configuration: Configuration = mock()
        val generator: Generator = mock()
        val source = "beautiful source code"

        given(analyser.analyse(element))
                .willReturn(document)
        given(configurationReader.read())
                .willReturn(configuration)
        given(generatorFactory.make(configuration))
                .willReturn(generator)
        given(generator.generate(document))
                .willReturn(source)

        // When
        interactor.process(element)

        // Then
        then(sourceWriter).should().write(document, "beautiful source code")
    }
}