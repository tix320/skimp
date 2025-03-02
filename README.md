# Skimp

**Skimp** is a lightweight Java utility library providing a variety of features designed to simplify common coding tasks and improve code readability. It includes utilities for collections, exceptions, functional programming, value generation, object manipulations, and threading.

## Packages Overview

- **collection**  
  Contains helper classes and methods to work with iterators, BiMaps, tuples, and more. These utilities reduce boilerplate when performing complex collection operations.

- **exception**  
  Offers utilities for handling and inspecting exceptions, including advanced stack trace manipulation for better debugging and logging.

- **function**  
  Focuses on functional programming aids, such as “unchecked” variants of `Runnable`, `Consumer`, and other functional interfaces. This allows you to throw checked exceptions without verbose exception handling in lambdas.

- **generator**  
  Provides scalar value generators—like thread-safe ID generators—and other sequence-producing utilities for unique identifiers or repetitive patterns.

- **object**  
  Supplies general-purpose object helpers, including an `IdentityObject` for distinguishing objects by identity rather than content, a `None` type for representing “no value” instead of `null`, a Cantor pairing function, and more.

- **thread**  
  Contains threading tools, most notably a **tracer** that preserves the stack trace of asynchronous calls, improving debuggability in concurrent or reactive applications.

## Installation

Add **Skimp** to your project using Maven or Gradle:

### Maven

```xml
<dependency>
    <groupId>com.github.tix320</groupId>
    <artifactId>skimp</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'com.github.tix320:skimp:2.0.0'
}
```

## Versioning

This project follows [Semantic Versioning](https://semver.org/).

## License

**Skimp** is released under the [Apache License, Version 2.0](LICENSE).

## Contact

- **Author**: [tix320](https://github.com/tix320)