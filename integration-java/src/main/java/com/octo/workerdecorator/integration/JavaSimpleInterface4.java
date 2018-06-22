package com.octo.workerdecorator.integration;

import com.octo.workerdecorator.annotation.Decorate;

import java.util.Date;

@Decorate(weak = true, mutable = true)
public interface JavaSimpleInterface4 {
    void a(int param);

    void b(Date param);
}
