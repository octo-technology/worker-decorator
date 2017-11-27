package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinInterfaceDecorated implements KotlinInterface {
  private final Executor executor;

  private final KotlinInterface decorated;

  public KotlinInterfaceDecorated(@NotNull Executor executor, @NotNull KotlinInterface decorated) {
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
  public void jim(@NotNull String arg0, @Nullable Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.jim(arg0, arg1);
      }
    });
  }
}
