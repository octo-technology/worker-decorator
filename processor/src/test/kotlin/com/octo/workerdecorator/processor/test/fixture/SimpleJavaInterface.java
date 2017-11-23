package com.octo.workerdecorator.processor.test.fixture;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Formatter;

public interface SimpleJavaInterface {
    void jon();

    void daenerys(@NotNull Date mother, int of, Formatter dragons);
}
