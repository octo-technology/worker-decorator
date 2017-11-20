package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;

public final class SimpleInterfaceDecorated implements SimpleInterface {
  private final Executor executor;

  private final SimpleInterface decorated;

  public SimpleInterfaceDecorated(@Nonnull Executor executor, @Nonnull SimpleInterface decorated) {
    this.executor = executor;
    this.decorated = decorated;
  }

  @Override
  public void pam() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.pam();
      }
    });
  }

  @Override
  public void jim(int arg0, String arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.jim(arg0, arg1);
      }
    });
  }
}
