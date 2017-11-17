package com.octo.workerdecorator.processor

import javax.lang.model.element.TypeElement

class Interactor(private val analyser: Analyser,
                 private val configurationReader: ConfigurationReader,
                 private val generatorFactory: GeneratorFactory,
                 private val sourceWriterFactory: SourceWriterFactory) {

    fun process(element: TypeElement) {
        val document = analyser.analyse(element)
        val configuration = configurationReader.read()
        val generator = generatorFactory.make(configuration)
        val writer = sourceWriterFactory.make(configuration)

        val source = generator.generate(document)
        writer.write(document, source)
    }
}