package com.octo.workerdecorator.processor.entity

data class Configuration(val language: Language,
                         val implementation: Implementation,
                         val mutability: Mutability)


enum class Language { JAVA, KOTLIN }

enum class Implementation { EXECUTOR, COROUTINE }

enum class Mutability { UNMUTABLE, MUTABLE }