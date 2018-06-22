package com.octo.workerdecorator.processor.test.fixture;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinInterfaceDecorated implements KotlinInterface {
  private final WeakReference<KotlinInterface> decorated;

  private final Executor executor;

  public KotlinInterfaceDecorated(@NotNull KotlinInterface decorated, @NotNull Executor executor) {
    this.decorated = new WeakReference<>(decorated);
    this.executor = executor;
  }

  @Override
  public void pam() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        KotlinInterface ref = decorated.get();
        if (ref != null) {
          ref.pam();
        }
      }
    });
  }

  @Override
  public void jim(@NotNull String arg0, @Nullable Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        KotlinInterface ref = decorated.get();
        if (ref != null) {
          ref.jim(arg0, arg1);
        }
      }
    });
  }
}
