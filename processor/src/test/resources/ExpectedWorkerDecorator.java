package com.octo.workerdecorator;

import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterface;
import com.octo.workerdecorator.processor.test.fixture.KotlinChildrenInterfaceDecorated;
import com.octo.workerdecorator.processor.test.fixture.KotlinInterface;
import com.octo.workerdecorator.processor.test.fixture.KotlinInterfaceDecorated;
import com.octo.workerdecorator.processor.test.fixture.KotlinParentInterface;
import com.octo.workerdecorator.processor.test.fixture.KotlinParentInterfaceDecorated;
import java.lang.Class;
import java.lang.RuntimeException;
import java.lang.SuppressWarnings;
import java.util.concurrent.Executor;

public class WorkerDecorator {
  @SuppressWarnings("unchecked")
  public static <DECORATED> WorkerDecoration<DECORATED> decorate(Class<DECORATED> clazz,
      Executor executor) {
    if (clazz == KotlinChildrenInterface.class) {
      return (WorkerDecoration<DECORATED>) new KotlinChildrenInterfaceDecorated(executor);
    }
    if (clazz == KotlinParentInterface.class) {
      return (WorkerDecoration<DECORATED>) new KotlinParentInterfaceDecorated(executor);
    }
    throw new RuntimeException("Impossible to find a decoration for " + clazz.getSimpleName());
  }

  public static KotlinInterface decorate(KotlinInterface implementation, Executor executor) {
    return new KotlinInterfaceDecorated(implementation, executor);
  }
}
