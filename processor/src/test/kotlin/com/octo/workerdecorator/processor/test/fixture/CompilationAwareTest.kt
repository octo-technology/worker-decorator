package com.octo.workerdecorator.processor.test.fixture

import com.google.testing.compile.CompilationRule
import com.octo.workerdecorator.processor.entity.Method
import com.octo.workerdecorator.processor.entity.Parameter
import org.junit.Rule
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
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

    fun executableElement(typeElement: TypeElement, name: String): ExecutableElement {
        return compilationRule.elements.getAllMembers(typeElement)
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .find { it.simpleName.toString() == name }!!
    }

    /*
     * Entity related helpers
     */

    fun methodFixture(name: String, parameters: List<Parameter>, typeElement: TypeElement): Method
            = Method(name, parameters, executableElement(typeElement, name))

    fun methodFixture(name: String, parameter: Parameter, typeElement: TypeElement): Method
            = methodFixture(name, listOf(parameter), typeElement)

    fun methodFixture(name: String, typeElement: TypeElement): Method
            = methodFixture(name, listOf(), typeElement)

    fun <T : Any> parametedFixture(name: String, `class`: KClass<T>): Parameter
            = Parameter(name, typeElement(`class`).asType())

    fun parametedFixture(name: String, kind: TypeKind): Parameter
            = Parameter(name, typeElement(kind))
}