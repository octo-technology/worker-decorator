package com.octo.workerdecorator.integration

import com.octo.workerdecorator.annotation.Decorate
import java.util.*

@Decorate
interface KotlinSimpleInterface {
    fun a(param: Int)
    fun b(param: Date)
}