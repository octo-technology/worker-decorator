package com.octo.workerdecorator.processor.entity

/**
 * Entity representing the options of the decoration to generate
 */
data class Configuration(
        /**
         * The wanted language for the generated decoration
         */
        val language: Language,
        /**
         * The wanted implementation for decorated methods
         */
        val implementation: Implementation,
        /**
         * Option specifying if the decoration should allow modifying the decorated instance
         */
        val mutability: Mutability)


enum class Language { JAVA, KOTLIN }

enum class Implementation { EXECUTOR, COROUTINE }

enum class Mutability { IMMUTABLE, MUTABLE }