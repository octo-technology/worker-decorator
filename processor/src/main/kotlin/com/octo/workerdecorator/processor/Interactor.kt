package com.octo.workerdecorator.processor

import com.octo.workerdecorator.annotation.Decorate
import javax.lang.model.element.TypeElement


class Interactor(private val analyser: Analyser,
                 private val configurationReader: ConfigurationReader,
                 private val generatorFactory: GeneratorFactory,
                 private val sourceWriterFactory: SourceWriterFactory) {

    fun process(element: TypeElement, annotation: Decorate) {
        val document = analyser.analyse(element)
        val configuration = configurationReader.read(annotation)
        val generator = generatorFactory.make(configuration)
        val writer = sourceWriterFactory.make(configuration)

        val source = generator.generate(document)
        writer.write(document, source)
    }
}