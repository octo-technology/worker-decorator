package com.octo.workerdecorator.processor.extension

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

fun TypeName.checkStringType() =
        if (this.toString() == "java.lang.String") ClassName("kotlin", "String") else this