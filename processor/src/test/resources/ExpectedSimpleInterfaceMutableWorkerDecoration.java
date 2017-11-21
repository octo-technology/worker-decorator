package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.util.Date;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SimpleInterfaceDecorated implements SimpleInterface {
  private final Executor executor;

  @Nullable
  private SimpleInterface decorated;

  public SimpleInterfaceDecorated(@Nonnull Executor executor) {
    this.executor = executor;
  }

  public void setDecorated(@Nullable SimpleInterface decorated) {
    this.decorated = decorated;
  }

  @Override
  public void pam() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          decorated.pam();
        }
      }
    });
  }

  @Override
  public void jim(int arg0, Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          decorated.jim(arg0, arg1);
        }
      }
    });
  }
}
