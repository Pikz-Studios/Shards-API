# Shards API Documentation

Official developer documentation for integrating with the **Shards** plugin.

***

## Getting Started

### Maven/Gradle Setup

The Shards API is available via JitPack.

#### Maven

Add the JitPack repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add the dependency:

```xml
<dependency>
    <groupId>com.github.Pikz-Studios</groupId>
    <artifactId>Shards-API</artifactId>
    <version>main-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

#### Gradle (Groovy)

Add the JitPack repository to your `build.gradle`:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency:

```groovy
dependencies {
    compileOnly 'com.github.Pikz-Studios:Shards-API:main-SNAPSHOT'
}
```

#### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Pikz-Studios:Shards-API:main-SNAPSHOT")
}
```

### Plugin Dependency

Add Shards as a dependency in your `plugin.yml`:

```yaml
depend: [Shards]
# Or use softdepend if Shards is optional
softdepend: [Shards]
```
