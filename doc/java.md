Java Decorations
---

|                                | Mutability | Reference |
|--------------------------------|------------|-----------|
| [Default decoration](#is)      | Immutable  | Strong    |
| [Weak decoration](#iw)         | Immutable  | Weak      |
| [Mutable decoration](#ms)      | Mutable    | Strong    |
| [Mutable weak decoration](#mw) | Mutable    | Weak      |

See also [kotlin decorations](kotlin.md).

## <a name="is"></a>Default decoration
The following interface:

```java
@Decorate
public interface SampleInterface {
    void stuff(int param);
    void things(boolean param);
}
```

Will trigger the generation of this decoration:

```java
public final class SampleInterfaceDecorated implements SampleInterface {
  private final SampleInterface decorated;
  private final Executor executor;

  public SampleInterfaceDecorated(
    @NotNull SampleInterface decorated,
    @NotNull Executor executor
  ) {
    this.decorated = decorated;
    this.executor = executor;
  }

  @Override
  public void stuff(@NotNull final int param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.stuff(param);
      }
    });
  }

  @Override
  public void things(@NotNull final boolean param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        decorated.things(param);
      }
    });
  }
}
```

And a helper method with the following signature:

```java
JavaSimpleInterface WorkerDecorator.decorate(
    JavaSimpleInterface implementation,
    Executor executor
)
```

It can thus be instantiated and used this way:

```java
SampleInterface implem = new SomeSampleInterfaceImplementation();
Executor executor = Executors.newSingleThreadExecutor();

SampleInterface decoration = new SampleInterfaceDecorated(
    implem,
    executor
);
// OR
SampleInterface decoration = WorkerDecorator.decorate(
    implem,
    executor
);

decoration.stuff(42);
decoration.things(true);
```

## <a name="iw"></a>Weak decoration
The following interface:

```java
@Decorate(weak = true)
public interface SampleInterface {
    void stuff(int param);
    void things(boolean param);
}
```

Will trigger the generation of this decoration:

```java
public final class SampleInterfaceDecorated implements SampleInterface {
  private final WeakReference<SampleInterface> decorated;
  private final Executor executor;

  public SampleInterfaceDecorated(
    @NotNull SampleInterface decorated,
    @NotNull Executor executor
  ) {
    this.decorated = new WeakReference<>(decorated);
    this.executor = executor;
  }

  @Override
  public void stuff(@NotNull final int param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        SampleInterface ref = decorated.get();
        if (ref != null) {
          ref.stuff(param);
        }
      }
    });
  }

  @Override
  public void things(@NotNull final boolean param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        SampleInterface ref = decorated.get();
        if (ref != null) {
          ref.things(param);
        }
      }
    });
  }
}
```

And a helper method with the following signature:

```java
JavaSimpleInterface WorkerDecorator.decorate(
    JavaSimpleInterface implementation,
    Executor executor
)
```

It can be instantiated and used like the default decoration.

## <a name="ms"></a>Mutable decoration
The following interface:

```java
@Decorate(mutable = true)
public interface SampleInterface {
    void stuff(int param);
    void things(boolean param);
}
```

Will trigger the generation of this decoration:

```java
public final class SampleInterfaceDecorated implements SampleInterface, WorkerDecoration<SampleInterface> {
  private final Executor executor;
  @Nullable private SampleInterface decorated;

  public SampleInterfaceDecorated(@NotNull Executor executor) {
    this.executor = executor;
  }

  @Override public void setDecorated(@Nullable SampleInterface decorated) {
    this.decorated = decorated;
  }

  @Override public void stuff(@NotNull final int param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          decorated.stuff(param);
        }
      }
    });
  }

  @Override public void things(@NotNull final boolean param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          decorated.things(param);
        }
      }
    });
  }

  @Override @NotNull public SampleInterface asType() {
    return this;
  }
}
```

And a helper method with the following signature:

```java
WorkerDecoration<JavaSimpleInterface> WorkerDecorator.decorate(
    Class<JavaSimpleInterface> clazz,
    Executor executor
)
```

It can thus be instantiated and used this way:

```java
SampleInterface implem = new SomeSampleInterfaceImplementation();
Executor executor = Executors.newSingleThreadExecutor();

SampleInterfaceDecorated decoration = new SampleInterfaceDecorated(executor);
// OR
WorkerDecoration<SampleInterface> decoration = WorkerDecorator.decorate(SampleInterface.class, executor);

// The generated decoration implements both `WorkerDecoration` and `SampleInterface`,
// The helper methods returns a `WorkerDecoration<SampleInterface>`
// The `setDecorated` method of `WorkerDecoration` allows you to change the decorated instance
// The `asType` method of `WorkerDecoration` allows you to retrieve the original interface

decoration.setDecorated(implem);
decoration.asType().stuff(42);
decoration.asType().things(true);
decoration.setDecorated(null);
```

## <a name="mw"></a>Mutable weak decoration
The following interface:

```java
@Decorate(mutable = true, weak = true)
public interface SampleInterface {
    void stuff(int param);
    void things(boolean param);
}
```

Will trigger the generation of this decoration:

```java
public final class SampleInterfaceDecorated implements SampleInterface, WorkerDecoration<SampleInterface> {
  private final Executor executor;
  @Nullable private WeakReference<SampleInterface> decorated;

  public SampleInterfaceDecorated(@NotNull Executor executor) {
    this.executor = executor;
  }

  @Override public void setDecorated(@Nullable SampleInterface decorated) {
    this.decorated = new WeakReference<>(decorated);
  }

  @Override public void stuff(@NotNull final int param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          SampleInterface ref = decorated.get();
          if (ref != null) {
            ref.stuff(param);
          }
        }
      }
    });
  }

  @Override public void things(@NotNull final boolean param) {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        if (decorated != null) {
          SampleInterface ref = decorated.get();
          if (ref != null) {
            ref.things(param);
          }
        }
      }
    });
  }

  @Override @NotNull public SampleInterface asType() {
    return this;
  }
}
```

And a helper method with the following signature:

```java
WorkerDecoration<JavaSimpleInterface> WorkerDecorator.decorate(
    Class<JavaSimpleInterface> clazz,
    Executor executor
)
```

It can be instantiated and used like the mutable decoration.


