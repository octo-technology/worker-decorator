package com.octo.workerdecorator.annotation

interface MutableWorkerDecoration<DECORATED> {
    var decorated: DECORATED?
    fun decoration(): DECORATED
}