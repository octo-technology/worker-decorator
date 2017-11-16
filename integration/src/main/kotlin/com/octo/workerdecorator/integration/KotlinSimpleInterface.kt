package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.Decorate

@Decorate
interface KotlinSimpleInterface {
    fun a(param: Int)
    fun b(param: String)
}