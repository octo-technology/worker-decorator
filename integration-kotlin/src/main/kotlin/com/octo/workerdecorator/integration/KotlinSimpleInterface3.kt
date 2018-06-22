package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.Decorate

@Decorate(weak = true)
interface KotlinSimpleInterface3 {
    fun a(param: Int)
    fun b(param: Double)
}