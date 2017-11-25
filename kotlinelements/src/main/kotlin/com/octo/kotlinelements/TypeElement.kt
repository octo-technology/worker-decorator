package com.octo.kotlinelements

import javax.lang.model.element.TypeElement

fun TypeElement.isProducedByKotlin(): Boolean
        = this.annotationMirrors.map { it.annotationType.toString() }.contains("kotlin.Metadata")