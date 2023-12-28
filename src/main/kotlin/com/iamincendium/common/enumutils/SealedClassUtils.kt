package com.iamincendium.common.enumutils

/**
 * Create a list of all objects implementing a specific sealed class or interface.
 *
 * *Note: It is generally a good idea to cache these values as they may be
 * expensive to create and are unlikely to change within an application's
 * lifetime.*
 *
 * Example:
 * ```kotlin
 * sealed class MySealedClass {
 *   data object OBJECT_A : MySealedClass()
 *   data object OBJECT_B : MySealedClass()
 *   data object OBJECT_C : MySealedClass()
 *   data object OBJECT_D : MySealedClass()
 * }
 *
 * fun main() {
 *   // Prints -> OBJECT_A, OBJECT_B, OBJECT_C, OBJECT_D
 *   println(sealedValues<MySealedClass>().joinToString(", ") { it::class.simpleName })
 * }
 * ```
 */
public inline fun <reified T : Any> sealedValues(): List<T> {
    require(T::class.isSealed) { "classInstance must be a sealed class" }

    return T::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

/**
 * Create a map of values that can be used to look up specific objects
 * implementing a sealed class or interface.
 *
 * Example:
 * ```kotlin
 * enum interface MySealedInterface {
 *   val name: String
 *
 *   data object ValueA : MySealedInterface {
 *     override val name: String = "A"
 *   }
 *   data object ValueB : MySealedInterface {
 *     override val name: String = "B"
 *   }
 *   data object ValueC : MySealedInterface {
 *     override val name: String = "C"
 *   }
 *   data class UnknownValue(override val name: String) : MySealedInterface
 *
 *   companion object {
 *     private val lookupMap = sealedObjectLookupMap<MySealedInterface> { it.name }
 *     fun lookup(name: String) = lookupMap[name] ?: UnknownValue(name)
 *   }
 * }
 *
 * fun main() {
 *   MySealedClass.lookup("A") // --> MySealedClass.ValueA
 *   MySealedClass.lookup("D") // --> MySealedClass.UnknownValue(name="D")
 * }
 * ```
 *
 * @param T the sealed class/interface type
 * @param V the value to use for lookups (usually a [String], must
 *          implement `hashCode`)
 * @param transformer a function to transform the object into a lookup string
 */
public inline fun <V, reified T : Any> sealedObjectLookupMap(transformer: (T) -> V): Map<V, T> =
    sealedValues<T>().associateBy(transformer)

/**
 * Create a map of values that can be used to look up specific objects in a
 * sealed class/interface based on the implementing object's class name.
 *
 * Example:
 * ```kotlin
 * enum class MySealedClass {
 *   data object ValueA : MySealedClass()
 *   data object ValueB : MySealedClass()
 *   data object ValueC : MySealedClass()
 *
 *   companion object {
 *     private val lookupMap = sealedObjectLookupMapFromClassName<MySealedClass>()
 *     fun lookup(name: String) = lookupMap[name]
 *   }
 * }
 *
 * fun main() {
 *   MySealedClass.lookup("ValueA") // --> MySealedClass.ValueA
 *   MySealedClass.lookup("ValueD") // --> null
 * }
 * ```
 *
 * @param T the sealed class/interface type
 * @param transformer a function to optionally transform the object class name into something else before writing into
 *                    the lookup map
 */
public inline fun <reified T : Any> sealedObjectLookupMapFromClassName(
    transformer: (String) -> String = { it },
): Map<String, T> = sealedObjectLookupMap { transformer(it.javaClass.simpleName) }
