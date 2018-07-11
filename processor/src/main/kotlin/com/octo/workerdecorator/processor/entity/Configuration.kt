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
     * Should the decorated instance be modifiable ?
     */
    val mutability: Mutability,
    /**
     * The strength of the reference to the decorated instance
     */
    val strength: ReferenceStrength
)

data class HelperConfiguration(
    val language: Language,
    val implementation: Implementation
)


enum class Language { JAVA, KOTLIN }

enum class Implementation { EXECUTOR, COROUTINES }

enum class Mutability { IMMUTABLE, MUTABLE }

enum class ReferenceStrength { STRONG, WEAK }