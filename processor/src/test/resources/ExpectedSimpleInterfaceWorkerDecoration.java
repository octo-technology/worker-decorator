package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinInterfaceDecorated implements KotlinInterface {
  private final KotlinInterface decorated;

  private final Executor executor;

  public KotlinInterfaceDecorated(@NotNull KotlinInterface decorated, @NotNull Executor executor) {
    this.decorated = decorated;
    this.executor = executor;
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
  public void jim(@NotNull final String arg0, @Nullable final Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.jim(arg0, arg1);
      }
    });
  }
}
