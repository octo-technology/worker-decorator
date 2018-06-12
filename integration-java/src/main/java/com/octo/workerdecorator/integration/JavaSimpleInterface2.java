package com.octo.workerdecorator.integration;

import com.octo.workerdecorator.annotation.Decorate;

@Decorate(mutable = true)
public interface JavaSimpleInterface2 {
    void a(int param);

    void b(Double param);
}
