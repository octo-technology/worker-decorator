package com.octo.kotlinelements

import com.squareup.kotlinpoet.*

/**
 * Translate a [TypeName] referencing a Java type into the corresponding Kotlin equivalent.
 *
 * Based on the [kotlin documentation](https://kotlinlang.org/docs/reference/java-interop.html) mapped types list.
 */
fun TypeName.asKotlinTypeName(): TypeName {

    // Recursion for parameterized types

    if (this is ParameterizedTypeName) {
        val rootType = this.rawType.asKotlinTypeName() as ClassName
        val parameterTypes = this.typeArguments.map { it.asKotlinTypeName() }.toTypedArray()

        val parameterizedTypeName = ParameterizedTypeName.get(rootType, *parameterTypes)

        if (parameterizedTypeName.rawType == ARRAY
            && parameterizedTypeName.typeArguments.size == 1
            && parameterizedTypeName.typeArguments[0] == INT
        ) {
            return IntArray::class.asTypeName()
        }

        return parameterizedTypeName
    }

    return when (this.toString()) {


    // Boxed primitives

        java.lang.Byte::class.java.name -> Byte::class.asTypeName()
        java.lang.Short::class.java.name -> Short::class.asTypeName()
        java.lang.Integer::class.java.name -> Int::class.asTypeName()
        java.lang.Long::class.java.name -> Long::class.asTypeName()
        java.lang.Character::class.java.name -> Char::class.asTypeName()
        java.lang.Float::class.java.name -> Float::class.asTypeName()
        java.lang.Double::class.java.name -> Double::class.asTypeName()
        java.lang.Boolean::class.java.name -> Boolean::class.asTypeName()


    // Non-primitive "built-ins"

        java.lang.Object::class.java.name -> Any::class.asTypeName()
        java.lang.Cloneable::class.java.name -> Cloneable::class.asTypeName()
        java.lang.Comparable::class.java.name -> Comparable::class.asTypeName()
        java.lang.Enum::class.java.name -> Enum::class.asTypeName()
        java.lang.annotation.Annotation::class.java.name -> Annotation::class.asTypeName()
        java.lang.Deprecated::class.java.name -> Deprecated::class.asTypeName()
        java.lang.CharSequence::class.java.name -> CharSequence::class.asTypeName()
        java.lang.String::class.java.name -> String::class.asTypeName()
        java.lang.Number::class.java.name -> Number::class.asTypeName()
        java.lang.Throwable::class.java.name -> Throwable::class.asTypeName()


    // Non typed collections

        java.util.Iterator::class.java.name -> Iterator::class.asTypeName()
        java.lang.Iterable::class.java.name -> Iterable::class.asTypeName()
        java.util.Collection::class.java.name -> Collection::class.asTypeName()
        java.util.Set::class.java.name -> Set::class.asTypeName()
        java.util.List::class.java.name -> List::class.asTypeName()
        java.util.ListIterator::class.java.name -> ListIterator::class.asTypeName()
        java.util.Map::class.java.name -> Map::class.asTypeName()
        "java.util.Entry" -> Map.Entry::class.asTypeName()


    // Java arrays

        java.lang.reflect.Array::class.java.name -> ARRAY


    // Already good :)

        else -> this
    }
}
