package com.octo.workerdecorator.processor.test.fixture;

import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinInterfaceDecorated implements KotlinInterface, WorkerDecoration<KotlinInterface> {
  private final Executor executor;

  @Nullable
  private WeakReference<KotlinInterface> decorated;

  public KotlinInterfaceDecorated(@NotNull Executor executor) {
    this.executor = executor;
  }

  @Override
  public void setDecorated(@Nullable KotlinInterface decorated) {
    this.decorated = new WeakReference<>(decorated);
  }

  @Override
  public void pam() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          KotlinInterface ref = decorated.get();
          if (ref != null) {
            ref.pam();
          }
        }
      }
    });
  }

  @Override
  public void jim(@NotNull final String arg0, @Nullable final Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          KotlinInterface ref = decorated.get();
          if (ref != null) {
            ref.jim(arg0, arg1);
          }
        }
      }
    });
  }

  @Override
  @NotNull
  public KotlinInterface asType() {
    return this;
  }
}
