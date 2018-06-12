package com.octo.workerdecorator.annotation

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.Executor

object WorkerDecorator {

    private val CONSTRUCTORS = mutableMapOf<Class<Any>, Constructor<Any>>()

    inline fun <reified T : Any> decorate(instance: T, executor: Executor): T {
        val constructor = findImmutableConstructor(T::class.java)
        try {
            return constructor.newInstance(executor, instance)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Unable to instantiate the decoration", e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> findImmutableConstructor(targetClass: Class<in T>): Constructor<T> {

        var constructor: Constructor<*>? = CONSTRUCTORS[targetClass]
        if (constructor != null) {
            return doTheCast(constructor)
        }

        constructor = try {
            val decorationClass = targetClass.classLoader.loadClass("${targetClass.name}Decorated")
            decorationClass.getConstructor(Executor::class.java, targetClass)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Unable to find $targetClass's decoration", e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find $targetClass's decoration constructor", e)
        }

        CONSTRUCTORS[targetClass as Class<Any>] = constructor as Constructor<Any>
        return doTheCast(constructor)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> doTheCast(constructor: Constructor<*>): Constructor<T> {
        val castedConstructor = constructor as? Constructor<T>
        if (castedConstructor != null) {
            return castedConstructor
        }

        throw RuntimeException("Unable to cast the constructor")
    }


}