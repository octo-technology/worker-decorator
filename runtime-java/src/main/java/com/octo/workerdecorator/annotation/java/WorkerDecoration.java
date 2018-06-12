package com.octo.workerdecorator.annotation.java;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WorkerDecoration<DECORATED> {

    void setDecorated(@Nullable DECORATED decorated);

    @NotNull DECORATED asType();
}
