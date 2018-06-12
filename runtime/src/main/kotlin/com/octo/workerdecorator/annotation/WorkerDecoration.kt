package com.octo.workerdecorator.annotation

interface WorkerDecoration<DECORATED> {
    var decorated: DECORATED?
    fun decoration(): DECORATED
}