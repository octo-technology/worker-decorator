package com.octo.workerdecorator.processor.entity

import org.jetbrains.annotations.Nullable
import javax.lang.model.type.TypeMirror

/**
 * Entity to represent the class to be generated
 */
data class Document(
        /**
         * The package of the interface we're decorating
         */
        val `package`: String,
        /**
         * The name of the class
         */
        val name: String,
        /**
         * A list of methods to implement
         */
        val methods: List<Method>,
        /**
         * The [TypeMirror] of the interface we're decorating
         */
        val typeMirror: TypeMirror,
        /**
         * True if the interface we're decorating is a Kotlin source file
         */
        val interfaceIsInKotlin: Boolean)

/**
 * Entity representing a method to override
 */
data class Method(
        /**
         * The name of the method
         */
        val name: String,
        /**
         * The list of arguments accepted by the method
         */
        val parameters: List<Parameter>)

/**
 * Entity representing a [Method] parameter
 */
data class Parameter(
        /**
         * The parameter name
         */
        val name: String,
        /**
         * The [TypeMirror] of the parameter
         */
        val typeMirror: TypeMirror,
        /**
         * True if the parameter is a kotlin optional type, or a java [Nullable]
         */
        val isOptional: Boolean) {

    // Ugly but true (needed for tests)
    // Overriding equals and hashcode to only consider the TypeMirror's name for equality checks :
    // Two instances of a TypeMirror (mirroring the same type) may not be equal…

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parameter

        if (name != other.name) return false
        if (typeMirror.toString() != other.typeMirror.toString()) return false
        if (isOptional != other.isOptional) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + typeMirror.toString().hashCode()
        result = 31 * result + isOptional.hashCode()
        return result
    }
}