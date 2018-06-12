package com.octo.workerdecorator.annotation

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.Executor

object WorkerDecorator {

    private val CONSTRUCTORS = mutableMapOf<Class<Any>, Constructor<Any>>()

    /**
     * Instantiate an immutable decoration for the given instance and executor
     */
    inline fun <reified T : Any> decorate(instance: T, executor: Executor): T {
        val constructor = findConstructor(T::class.java)
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

    /**
     * Instantiate a mutable decoration for the wanted type and executor
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> decorate(executor: Executor): WorkerDecoration<T> {
        val constructor = findConstructor(T::class.java, mutable = true)
        try {
            return constructor.newInstance(executor) as? WorkerDecoration<T>
                    ?: throw RuntimeException("Unable to cast the constructor")
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Unable to instantiate the decoration", e)
        }
    }


    /*
     * Private and Semi-Private methods
     */

    @Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
    fun <T : Any> findConstructor(targetClass: Class<in T>, mutable: Boolean = false): Constructor<T> {

        var constructor: Constructor<*>? = CONSTRUCTORS[targetClass]
        if (constructor != null) {
            return doTheCast(constructor)
        }

        constructor = try {
            val decorationClass = targetClass.classLoader.loadClass("${targetClass.name}Decorated")

            if (mutable) {
                decorationClass.getConstructor(Executor::class.java)
            } else {
                decorationClass.getConstructor(Executor::class.java, targetClass)
            }
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