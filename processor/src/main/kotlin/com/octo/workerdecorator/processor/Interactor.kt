package com.octo.workerdecorator.processor

import javax.lang.model.element.TypeElement

class Interactor(private val analyser: Analyser,
                 private val configurationReader: ConfigurationReader,
                 private val generatorFactory: GeneratorFactory,
                 private val sourceWriter: SourceWriter) {

    fun process(element: TypeElement) {
        val document = analyser.analyse(element)
        val configuration = configurationReader.read()
        val generator = generatorFactory.make(configuration)

        val source = generator.generate(document)
        sourceWriter.write(document, source)
    }
}