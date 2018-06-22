package com.octo.workerdecorator.processor.test

import com.google.testing.compile.CompilationRule
import com.octo.workerdecorator.processor.entity.*
import com.octo.workerdecorator.processor.entity.Mutability.IMMUTABLE
import com.octo.workerdecorator.processor.entity.Mutability.MUTABLE
import com.octo.workerdecorator.processor.entity.ReferenceStrength.STRONG
import com.octo.workerdecorator.processor.entity.ReferenceStrength.WEAK
import com.octo.workerdecorator.processor.test.fixture.JavaInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface
import com.octo.workerdecorator.processor.test.fixture.KotlinParentInterface
import org.junit.Rule
import java.util.*
import javax.lang.model.element.TypeElement
import javax.lang.model.type.PrimitiveType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeKind.INT
import kotlin.reflect.KClass

open class CompilationAwareTest {

    @JvmField
    @Rule
    var compilationRule = CompilationRule()

    /*
     * Type related helpers
     */

    fun <T : Any> typeElement(`class`: KClass<T>): TypeElement =
        compilationRule.elements.getTypeElement(`class`.java.name)

    fun typeElement(kind: TypeKind): PrimitiveType = compilationRule.types.getPrimitiveType(kind)

    /*
     * Entity related helpers
     */

    fun methodFixture(name: String, parameters: List<Parameter>): Method = Method(name, parameters)

    fun methodFixture(name: String, parameter: Parameter): Method = methodFixture(name, listOf(parameter))

    fun methodFixture(name: String): Method = methodFixture(name, listOf())

    fun <T : Any> parameterFixture(name: String, `class`: KClass<T>, optional: Boolean = false): Parameter =
        Parameter(name, typeElement(`class`).asType(), optional)

    fun parameterFixture(name: String, kind: TypeKind): Parameter =
        Parameter(name, typeElement(kind), false) // Non-boxed primitives never are optional

    /*
     * More complex fixtures
     */

    fun simpleInterfaceFixture(): DecorationDocument {
        val typeElement = typeElement(KotlinInterface::class)
        val methods = listOf(
            methodFixture("pam"),
            methodFixture(
                "jim",
                listOf(
                    parameterFixture("arg0", String::class, false),
                    parameterFixture("arg1", Date::class, true)
                )
            )
        )

        return DecorationDocument(
            "com.octo.workerdecorator.processor.test.fixture",
            "KotlinInterfaceDecorated",
            methods,
            typeElement.asType(),
            true
        )
    }

    fun simpleJavaInterfaceFixture(): DecorationDocument {
        val typeElement = typeElement(JavaInterface::class)
        val methods = listOf(
            methodFixture("jon"),
            methodFixture(
                "daenerys",
                listOf(
                    parameterFixture("arg0", String::class, false),
                    parameterFixture("arg1", INT),
                    parameterFixture("arg2", Formatter::class, true)
                )
            )
        )

        return DecorationDocument(
            "com.octo.workerdecorator.processor.test.fixture",
            "JavaInterfaceDecorated",
            methods,
            typeElement.asType(),
            false
        )
    }

    fun aggregateFixture(): List<AggregateDocument> {
        return listOf(
            AggregateDocument(
                "com.octo.workerdecorator.processor.test.fixture",
                "KotlinInterfaceDecorated",
                typeElement(KotlinInterface::class).asType(),
                IMMUTABLE,
                STRONG
            ),
            AggregateDocument(
                "com.octo.workerdecorator.processor.test.fixture",
                "KotlinChildrenInterfaceDecorated",
                typeElement(KotlinChildrenInterface::class).asType(),
                MUTABLE,
                STRONG
            ),
            AggregateDocument(
                "com.octo.workerdecorator.processor.test.fixture",
                "KotlinParentInterfaceDecorated",
                typeElement(KotlinParentInterface::class).asType(),
                MUTABLE,
                WEAK
            )
        )
    }
}