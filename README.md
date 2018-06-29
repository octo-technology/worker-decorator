Worker Decorator
---

Worker Decorator decorates your objects to facilitate navigating between the synchronous and asynchronous sides of your code.

The OCTO Technology mobile squad has been heavily using it to implement its Android vision of a Clean Architecture for the past 2 years.

As of today, it can generate decorations in Kotlin or Java, holding a Strong or Weak reference to the decorated object, relying on an Executor to handle the thread change.

1. [Sample](#sample)
2. [Exemple usage](#real-usage)
3. [Decoration options](#options)
4. [Using it in your project](#using)
5. [Future plans](#future)

## <a name="quick-usage"></a>Sample
Annotating an interface with `@Decorate` will generate a decoration wrapping calls to the actual implementation in another thread.

The following interface:

```kotlin
@Decorate
interface ImportantInterface {
    fun doSeriousStuff(count: Int)
    fun someOtherLongOperation()
}
```

Will trigger the generation of this class:

```kotlin
class ImportantInterfaceDecorated(private val decorated: ImportantInterface, private val executor: Executor) : ImportantInterface {
    override fun doSeriousStuff(count: Int) {
        executor.execute { decorated.doSeriousStuff(count) }
    }

    override fun someOtherLongOperation() {
        executor.execute { decorated.someOtherLongOperation() }
    }
}
```

As a result, calling the `doSeriousStuff` method on the decorated object will simply deffer the real call to whatever thread(s) hiding behind the `Executor` you passed.

In addition to the generated class, a helper method is written to ease the instantiation of the decoration.

```kotlin
val decoration = ImportantInterfaceDecorated(realImplementation, executor) // "Natural" way
val coolerDecoration = WorkerDecorator.decorate(realImplementation, executor) // Helper method way
```

The `decorate` method helps you avoid having to import or know the decoration. The method can be statically imported, thus reducing the verbosity of the instantiations (for example in your Dagger modules).

There are a [number of options](#options) for you to customize the decorations that are explained below.

## <a name="real-usage"></a>Example "real-life" usage
This section is WIP

## <a name="options"></a>Decoration options
Simply annotating an interface with `@Decorate` will trigger the generation of an immutable strong reference decoration, as seen in the [sample section](#sample) above.

`@Decorate` can take two options:

```kotlin
@Decorate(
    weak = true,    // default = false
    mutable = true  // default = false
)
```

Specifying `weak = true` makes the Decoration hold a `WeakReference` to the implementation.

Specifying `mutable = true` makes the decorated implementation editable. The Decoration constructor does not need the implementation instance, it can be set with a `setDecorated()` method.

All the possible configuration combinations and the resulting generated files are detailed in the [doc/kotlin.md](doc/kotlin.md) and [doc/java.md](doc/java.md) files.

## <a name="using"></a>Using it in your project
![Worker Decorator latest version][badge]

### Kotlin
If you want your decorations to be generated in Kotlin, you first need to apply the `kotlin-kapt` plugin in your module's gradle build file.

Then add the following dependencies, replacing `VERSION` with the wanted version.

```groovy
implementation "com.octo.workerdecorator:workerdecorator:VERSION"
kapt "com.octo.workerdecorator:workerdecorator-processor:VERSION"
```

### Java
Intellij and Android Studio still have trouble "seeing" and providing auto-completion on kotlin generated code, even though your project will compile without any problem.

For this reason, or simply because your project can be in plain Java, you can prefer to have your decorations generated in Java.

If your module contains Kotlin code, you first need to apply the `kotlin-kapt` plugin in your module's gradle build file and add the following dependencies, replacing `VERSION` with the wanted version.

```groovy
implementation "com.octo.workerdecorator:workerdecorator-java:VERSION"
kapt "com.octo.workerdecorator:workerdecorator-processor-java:VERSION"
```

If your module is a plain Java module, you first need to add an annotation processor to your build (you can for example use the [gradle-apt-plugin](https://github.com/tbroyer/gradle-apt-plugin)). If your module is a Java-only Android module, you can rely on the provided annotation processor.

Add the following dependencies, replacing `VERSION` with the wanted version.

```groovy
implementation "com.octo.workerdecorator:workerdecorator-java:VERSION"
annotationProcessor "com.octo.workerdecorator:workerdecorator-processor-java:VERSION"
```

## <a name="future"></a>Future plans
Future plans include offering more configuration options, for example using Kotlin Coroutines instead of classic Executors.

If you have use-cases that you find interesting and not supported by the library, please open an Issue to discuss it.

[badge]: https://api.bintray.com/packages/octomob/maven/workerdecorator/images/download.svg "Latest version badge"