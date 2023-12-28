# Kotlin - Enum Utils

![Maven Central](https://img.shields.io/maven-central/v/com.iamincendium.common/enum-utils)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.iamincendium.common/enum-utils?server=https%3A%2F%2Foss.sonatype.org)
![GitHub](https://img.shields.io/github/license/incendium/enum-utils)

This project is a simple utility library that helps in constructing lookup tables for enums and creating sealed class 
hierarchies that function similar to enums.  The latter can be useful if for maintaining type safety in the event that 
an unknown value is received, but preserving the value is desirable over converting it to an standardized "UNKNOWN" 
value.

### Installation

Add the following to your `gradle.build` or `gradle.build.kts` file:

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.iamincendium.common:enum-utils:0.1.0")
}
```

Refer to the KDocs for more information on usage.
