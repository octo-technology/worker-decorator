package com.octo.workerdecorator.processor.test.fixture

import com.google.testing.compile.CompilationRule
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Parameter
import org.junit.Rule
import javax.lang.model.type.TypeKind
import kotlin.reflect.KClass

open class CompilationAwareTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    /*
     * Type related helpers
     */

    fun <T : Any> typeElement(`class`: KClass<T>)
            = compilationRule.elements.getTypeElement(`class`.java.name)

    fun typeElement(kind: TypeKind)
            = compilationRule.types.getPrimitiveType(kind)

    /*
     * Entity related helpers
     */

    fun methodFixture(name: String, parameters: List<Parameter>): Method
            = Method(name, parameters)

    fun methodFixture(name: String, parameter: Parameter): Method
            = methodFixture(name, listOf(parameter))

    fun methodFixture(name: String): Method
            = methodFixture(name, listOf())

    fun <T : Any> parameterFixture(name: String, `class`: KClass<T>): Parameter
            = Parameter(name, typeElement(`class`).asType())

    fun parameterFixture(name: String, kind: TypeKind): Parameter
            = Parameter(name, typeElement(kind))
}