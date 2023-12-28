package com.iamincendium.kotlin.enumutils

/**
 * Look up an enum or return `null` if no matching enum was found.
 *
 * This is an alternative to `enumValueOf` and works similarly to Apache common-lang's `EnumUtils.getEnum()`.
 */
public inline fun <reified T : Enum<T>> enumValueOfOrNull(value: String?): T? = if (value == null) {
    null
} else try {
    enumValueOf<T>(value)
} catch (ignore: IllegalArgumentException) {
    null
}

/**
 * Create a map of values that can be used to look up specific enums.
 *
 * Example:
 * ```kotlin
 * enum class MyEnum {
 *   VALUE_A, VALUE_B, VALUE_C;
 *
 *   companion object {
 *     private val lookupMap = enumLookupMap<MyEnum> { it.name }
 *     fun lookup(name: String) = lookupMap[name]
 *   }
 * }
 *
 * fun main() {
 *   MyEnum.lookup("VALUE_A") // --> MyEnum.VALUE_A
 * }
 * ```
 *
 * @param T the enum type
 * @param V the value to use for lookups (usually a [String], must implement `hashCode`)
 * @param transformer a lambda to transform the enum value into a lookup string
 */
public inline fun <V, reified T : Enum<T>> enumLookupMap(transformer: (T) -> V?): Map<V, T> = buildMap {
    enumValues<T>().forEach { enum: T -> transformer(enum)?.let { put(it, enum) } }
}
