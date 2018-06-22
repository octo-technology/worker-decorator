package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.Decorate

@Decorate(weak = true, mutable = true)
interface KotlinSimpleInterface4 {
    fun a(param: Int)
    fun b(param: Double)
}