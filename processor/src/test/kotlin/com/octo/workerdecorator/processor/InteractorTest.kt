package com.octo.workerdecorator.processor

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers.RETURNS_DEEP_STUBS
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.lang.model.element.TypeElement

@RunWith(MockitoJUnitRunner::class)
class InteractorTest {

    @Mock
    private lateinit var analyser: Analyser
    @Mock
    private lateinit var configurationMaker: ConfigurationMaker
    @Mock
    private lateinit var generatorFactory: GeneratorFactory
    @Mock
    private lateinit var sourceWriterFactory: SourceWriterFactory
    @InjectMocks
    private lateinit var interactor: Interactor

    @Test
    fun `interactor orchestrates processing correctly`() {
        // Given
        val element: TypeElement = mock()
        val annotation: Decorate = mock()
        val document: DecorationDocument = mock()
        val configuration: Configuration = mock(defaultAnswer = RETURNS_DEEP_STUBS)
        val generator: Generator = mock()
        val sourceWriter: SourceWriter = mock()
        val source = "beautiful source code"

        given(analyser.analyse(element))
            .willReturn(document)
        given(configurationMaker.read(annotation))
            .willReturn(configuration)
        given(generatorFactory.make(configuration))
            .willReturn(generator)
        given(sourceWriterFactory.make(configuration.language))
            .willReturn(sourceWriter)
        given(generator.generate(document))
            .willReturn(source)

        // When
        interactor.process(element, annotation)

        // Then
        then(sourceWriter).should().write(document, "beautiful source code")
    }

    @Test
    fun `interactor orchestrates aggregation correctly`() {
        // Given
        val element: TypeElement = mock()
        val annotation: Decorate = mock()
        val aggregateDocument: AggregateDocument = mock()
        val document: Document = AggregateDocument.AGGREGATOR
        val configuration = AggregateConfiguration(mock(), mock())
        val generator: AggregateGenerator = mock()
        val sourceWriter: SourceWriter = mock()
        val source = "beautiful source code"

        val data = listOf(Pair(element, annotation))
        val documents = listOf(aggregateDocument)

        given(analyser.analyse(data))
            .willReturn(documents)
        given(configurationMaker.read())
            .willReturn(configuration)
        given(generatorFactory.makeAggregator(configuration))
            .willReturn(generator)
        given(sourceWriterFactory.make(configuration.language))
            .willReturn(sourceWriter)
        given(generator.generate(document, documents))
            .willReturn(source)

        // When
        interactor.aggregate(data)

        // Then
        then(sourceWriter).should().write(document, "beautiful source code")
    }
}