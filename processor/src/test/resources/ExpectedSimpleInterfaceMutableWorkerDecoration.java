package com.octo.workerdecorator.processor.test.fixture;

import com.octo.workerdecorator.annotation.java.WorkerDecoration;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.String;
import java.util.Date;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinInterfaceDecorated implements KotlinInterface, WorkerDecoration<KotlinInterface> {
  private final Executor executor;

  @Nullable
  private KotlinInterface decorated;

  public KotlinInterfaceDecorated(@NotNull Executor executor) {
    this.executor = executor;
  }

  @Override
  public void setDecorated(@Nullable KotlinInterface decorated) {
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
  public void jim(@NotNull String arg0, @Nullable Date arg1) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          decorated.jim(arg0, arg1);
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
