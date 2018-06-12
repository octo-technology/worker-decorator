package com.octo.workerdecorator.integration;

import com.octo.workerdecorator.annotation.Decorate;

import java.util.Date;

@Decorate
public interface JavaSimpleInterface {
    void a(int param);

    void b(Date param);
}
