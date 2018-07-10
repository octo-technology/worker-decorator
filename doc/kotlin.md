Kotlin Decorations
---

|                                | Mutability | Reference |
|--------------------------------|------------|-----------|
| [Default decoration](#is)      | Immutable  | Strong    |
| [Weak decoration](#iw)         | Immutable  | Weak      |
| [Mutable decoration](#ms)      | Mutable    | Strong    |
| [Mutable weak decoration](#mw) | Mutable    | Weak      |

See also [java decorations](java.md).

## <a name="is"></a>Default decoration
The following interface:

```kotlin
@Decorate
interface SampleInterface {
    fun stuff(param: Int)
    fun things(param: Boolean)
}
```

Will trigger the generation of this decoration:

```kotlin
class SampleInterfaceDecorated(
    private val decorated: SampleInterface,
    private val executor: Executor
) : SampleInterface {
    override fun stuff(param: Int) {
        executor.execute { decorated.stuff(param) }
    }

    override fun things(param: Boolean) {
        executor.execute { decorated.things(param) }
    }
}
```

And a helper method with the following signature:

```kotlin
fun WorkerDecorator.decorate(
    implementation: SampleInterface,
    executor: Executor
): SampleInterface
```

It can thus be instantiated and used this way:

```kotlin
val implem = SomeSampleInterfaceImplementation()
val executor = Executors.newSingleThreadExecutor()

val decoration = SampleInterfaceDecorated(implem, executor)
// OR
val decoration = WorkerDecorator.decorate(implem, executor)

decoration.stuff(42)
decoration.things(true)
```

## <a name="iw"></a>Weak decoration
The following interface:

```kotlin
@Decorate(weak = true)
interface SampleInterface {
    fun stuff(param: Int)
    fun things(param: Boolean)
}
```

Will trigger the generation of this decoration:

```kotlin
class SampleInterfaceDecorated(
    decorated: SampleInterface,
    private val executor: Executor
) : SampleInterface {
    private val decorated: WeakReference<SampleInterface> = WeakReference(decorated)

    override fun stuff(param: Int) {
        executor.execute { decorated.get()?.stuff(param) }
    }

    override fun things(param: Boolean) {
        executor.execute { decorated.get()?.things(param) }
    }
}
```

And a helper method with the following signature:

```kotlin
fun WorkerDecorator.decorate(
    implementation: SampleInterface,
    executor: Executor
): SampleInterface
```

It can be instantiated and used like the default decoration.

## <a name="ms"></a>Mutable decoration
The following interface:

```kotlin
@Decorate(mutable = true)
interface SampleInterface {
    fun stuff(param: Int)
    fun things(param: Boolean)
}
```

Will trigger the generation of this decoration:

```kotlin
class SampleInterfaceDecorated(private val executor: Executor) :
    SampleInterface,
    WorkerDecoration<SampleInterface> {

    private var decorated: SampleInterface? = null

    override fun stuff(param: Int) {
        executor.execute { decorated?.stuff(param) }
    }

    override fun things(param: Boolean) {
        executor.execute { decorated?.things(param) }
    }

    override fun setDecorated(decorated: SampleInterface?) {
        this.decorated = decorated
    }

    override fun asType(): SampleInterface = this
}
```

And a helper method with the following signature:

```kotlin
fun WorkerDecorator.decorate<SampleInterface>(executor: Executor):
    WorkerDecoration<SampleInterface>
```

It can thus be instantiated and used this way:

```kotlin
val implem = SomeSampleInterfaceImplementation()
val executor = Executors.newSingleThreadExecutor()

val decoration = SampleInterfaceDecorated(executor)
// OR
val decoration = WorkerDecorator.decorate<SampleInterface>(executor)
// OR (if the type can be inferred)
val decoration: WorkerDecoration<SampleInterface> = WorkerDecorator.decorate(executor)

// The generated decoration implements both `WorkerDecoration` and `SampleInterface`,
// The helper methods returns a `WorkerDecoration<SampleInterface>`
// The `setDecorated` method of `WorkerDecoration` allows you to change the decorated instance
// The `asType` method of `WorkerDecoration` allows you to retrieve the original interface

decoration.setDecorated(implem)
decoration.asType().stuff(42)
decoration.asType().things(true)
decoration.setDecorated(null)
```

## <a name="mw"></a>Mutable weak decoration
The following interface:

```kotlin
@Decorate(mutable = true, weak = true)
interface SampleInterface {
    fun stuff(param: Int)
    fun things(param: Boolean)
}
```

Will trigger the generation of this decoration:

```kotlin
class SampleInterfaceDecorated(private val executor: Executor) :
    SampleInterface,
    WorkerDecoration<SampleInterface> {

    private var decorated: WeakReference<SampleInterface?>? = null

    override fun stuff(param: Int) {
        executor.execute { decorated?.get()?.stuff(param) }
    }

    override fun things(param: Boolean) {
        executor.execute { decorated?.get()?.things(param) }
    }

    override fun setDecorated(decorated: SampleInterface?) {
        this.decorated = WeakReference(decorated)
    }

    override fun asType(): SampleInterface = this
}
```

And a helper method with the following signature:

```kotlin
fun WorkerDecorator.decorate<SampleInterface>(executor: Executor):
    WorkerDecoration<SampleInterface>
```

It can be instantiated and used like the mutable decoration.


