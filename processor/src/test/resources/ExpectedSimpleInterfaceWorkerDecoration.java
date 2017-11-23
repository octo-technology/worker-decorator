package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SimpleInterfaceDecorated implements SimpleInterface {
  private final Executor executor;

  private final SimpleInterface decorated;

  public SimpleInterfaceDecorated(@NotNull Executor executor, @NotNull SimpleInterface decorated) {
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
  public void jim(@NotNull Currency arg0, @Nullable Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.jim(arg0, arg1);
      }
    });
  }
}
