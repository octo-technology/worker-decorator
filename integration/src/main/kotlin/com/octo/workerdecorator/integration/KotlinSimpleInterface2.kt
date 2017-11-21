package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.Decorate

@Decorate(decoratedObjectIsMutable = true)
interface KotlinSimpleInterface2 {
    fun a(param: Int)
    fun b(param: Double)
}