package com.octo.kotlinelements

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@Suppress("LocalVariableName")
class KotlinPoetTypeNameTest {

    @Test
    fun `converts Java boxed types`() {
        assertThat(typeNameFor(java.lang.Byte::class.java).asKotlinTypeName())
                .isEqualTo(Byte::class.asTypeName())
        assertThat(typeNameFor(java.lang.Short::class.java).asKotlinTypeName())
                .isEqualTo(Short::class.asTypeName())
        assertThat(typeNameFor(java.lang.Integer::class.java).asKotlinTypeName())
                .isEqualTo(Int::class.asTypeName())
        assertThat(typeNameFor(java.lang.Long::class.java).asKotlinTypeName())
                .isEqualTo(Long::class.asTypeName())
        assertThat(typeNameFor(java.lang.Character::class.java).asKotlinTypeName())
                .isEqualTo(Char::class.asTypeName())
        assertThat(typeNameFor(java.lang.Float::class.java).asKotlinTypeName())
                .isEqualTo(Float::class.asTypeName())
        assertThat(typeNameFor(java.lang.Double::class.java).asKotlinTypeName())
                .isEqualTo(Double::class.asTypeName())
        assertThat(typeNameFor(java.lang.Boolean::class.java).asKotlinTypeName())
                .isEqualTo(Boolean::class.asTypeName())
    }

    @Test
    fun `converts non primitive built-ins`() {
        assertThat(typeNameFor(java.lang.Object::class.java).asKotlinTypeName())
                .isEqualTo(Any::class.asTypeName())
        assertThat(typeNameFor(java.lang.Cloneable::class.java).asKotlinTypeName())
                .isEqualTo(Cloneable::class.asTypeName())
        assertThat(typeNameFor(java.lang.Comparable::class.java).asKotlinTypeName())
                .isEqualTo(Comparable::class.asTypeName())
        assertThat(typeNameFor(java.lang.Enum::class.java).asKotlinTypeName())
                .isEqualTo(Enum::class.asTypeName())
        assertThat(typeNameFor(java.lang.annotation.Annotation::class.java).asKotlinTypeName())
                .isEqualTo(Annotation::class.asTypeName())
        assertThat(typeNameFor(java.lang.Deprecated::class.java).asKotlinTypeName())
                .isEqualTo(Deprecated::class.asTypeName())
        assertThat(typeNameFor(java.lang.CharSequence::class.java).asKotlinTypeName())
                .isEqualTo(CharSequence::class.asTypeName())
        assertThat(typeNameFor(java.lang.String::class.java).asKotlinTypeName())
                .isEqualTo(String::class.asTypeName())
        assertThat(typeNameFor(java.lang.Number::class.java).asKotlinTypeName())
                .isEqualTo(Number::class.asTypeName())
        assertThat(typeNameFor(java.lang.Throwable::class.java).asKotlinTypeName())
                .isEqualTo(Throwable::class.asTypeName())
    }

    @Test
    fun `converts non-typed collections`() {
        assertThat(typeNameFor(java.util.Iterator::class.java).asKotlinTypeName())
                .isEqualTo(Iterator::class.asTypeName())
        assertThat(typeNameFor(java.lang.Iterable::class.java).asKotlinTypeName())
                .isEqualTo(Iterable::class.asTypeName())
        assertThat(typeNameFor(java.util.Collection::class.java).asKotlinTypeName())
                .isEqualTo(Collection::class.asTypeName())
        assertThat(typeNameFor(java.util.Set::class.java).asKotlinTypeName())
                .isEqualTo(Set::class.asTypeName())
        assertThat(typeNameFor(java.util.List::class.java).asKotlinTypeName())
                .isEqualTo(List::class.asTypeName())
        assertThat(typeNameFor(java.util.ListIterator::class.java).asKotlinTypeName())
                .isEqualTo(ListIterator::class.asTypeName())
        assertThat(typeNameFor(java.util.Map::class.java).asKotlinTypeName())
                .isEqualTo(Map::class.asTypeName())
        assertThat(typeNameFor(java.util.Map.Entry::class.java).asKotlinTypeName())
                .isEqualTo(Map.Entry::class.asTypeName())
    }

    @Test
    fun `converts parameterized types`() {
        val `Java List•String•` = ParameterizedTypeName.get(java.util.List::class.java, java.lang.String::class.java)
        val `Java Map•List·String·,Number•` = ParameterizedTypeName.get(typeNameFor(java.util.Map::class.java),
                `Java List•String•`, typeNameFor(java.lang.Number::class.java))

        val `Kotlin List•String•` = ParameterizedTypeName.get(List::class, String::class)
        val `Kotlin Map•List·String·,Number•` = ParameterizedTypeName.get(Map::class.asClassName(),
                `Kotlin List•String•`, Number::class.asClassName())

        assertThat(`Java List•String•`.asKotlinTypeName()).isEqualTo(`Kotlin List•String•`)
        assertThat(`Java Map•List·String·,Number•`.asKotlinTypeName()).isEqualTo(`Kotlin Map•List·String·,Number•`)
    }

    private fun <T : Any> typeNameFor(`class`: Class<T>): ClassName
            = ClassName(`class`.`package`.name, `class`.simpleName.split(".").last())
}