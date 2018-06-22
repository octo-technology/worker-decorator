package com.octo.workerdecorator.annotation

interface WeakWorkerDecoration<DECORATED> {
    fun setDecorated(decorated: DECORATED?)
    fun asType(): DECORATED
}