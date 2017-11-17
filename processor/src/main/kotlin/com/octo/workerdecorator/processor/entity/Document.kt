package com.octo.workerdecorator.processor.entity

import javax.lang.model.type.TypeMirror

data class Document(val `package`: String,
                    val name: String,
                    val methods: List<Method>,
                    val originalTypeMirror: TypeMirror)

data class Method(val name: String,
                  val parameters: List<Parameter>)

data class Parameter(val name: String, val typeMirror: TypeMirror) {

    // Ugly but true… (needed for tests)
    // Overriding equals and hashcode to only consider the TypeMirror name for equality checks

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Parameter

        if (name != other.name) return false
        if (typeMirror.toString() != other.typeMirror.toString()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + typeMirror.toString().hashCode()
        return result
    }
}