package com.octo.workerdecorator.annotation

interface KotlinMutableDecoration<DECORATED> {
    var decorated: DECORATED?
    fun decoration(): DECORATED
}