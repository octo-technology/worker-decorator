package com.octo.workerdecorator.processor.entity

import java.lang.reflect.Method

data class Document(val name: String, val methods: List<Method>)