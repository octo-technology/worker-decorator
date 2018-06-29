package com.octo.workerdecorator.processor

import com.octo.workerdecorator.annotation.Decorate
import com.octo.workerdecorator.processor.entity.HelperDocument
import javax.lang.model.element.TypeElement

/**
 * Class orchestrating the processing of an annotated interface
 */
class Interactor(
    private val analyser: Analyser,
    private val configurationReader: ConfigurationReader,
    private val generatorFactory: GeneratorFactory,
    private val sourceWriterFactory: SourceWriterFactory
) {

    fun process(element: TypeElement, annotation: Decorate) {
        val document = analyser.analyse(element)
        val configuration = configurationReader.read(annotation)
        val generator = generatorFactory.make(configuration)
        val writer = sourceWriterFactory.make(configuration.language)

        val source = generator.generate(document)
        writer.write(document, source)
    }

    fun aggregate(data: List<Pair<TypeElement, Decorate>>) {
        if (data.isEmpty()) return

        val documents = analyser.analyse(data)
        val configuration = configurationReader.read()
        val generator = generatorFactory.makeAggregator(configuration)
        val writer = sourceWriterFactory.make(configuration.language)

        val source = generator.generate(HelperDocument.AGGREGATOR, documents)
        writer.write(HelperDocument.AGGREGATOR, source)
    }
}