@file:Suppress("unused")

package coil

import org.gradle.api.Project
import kotlin.math.pow

val Project.minSdk: Int
    get() = intProperty("minSdk")

val Project.targetSdk: Int
    get() = intProperty("targetSdk")

val Project.compileSdk: Int
    get() = intProperty("compileSdk")

val Project.versionName: String
    get() = stringProperty("VERSION_NAME")

val Project.versionCode: Int
    get() = versionName
        .takeWhile { it.isDigit() || it == '.' }
        .split('.')
        .map { it.toInt() }
        .reversed()
        .sumByIndexed { index, unit ->
            // 1.2.3 -> 102030
            (unit * 10.0.pow(2 * index + 1)).toInt()
        }

/**
 * Enable this property to run the benchmarks with R8 enabled.
 * R8 optimises the libraries and should offer a more optimal benchmark.
 * You can enable this property ad hoc (e.g. `./gradlew benchmark-coil:connectedCheck -DenableR8=true`).
 */
val Project.isR8Enabled: Boolean
    get() = System.getProperty("enableR8", "false")!!.toBoolean()

/**
 * Enable this property to run the benchmark with native code enabled.
 * You can enable this property ad hoc (e.g. `./gradlew benchmark-coil:connectedCheck -DenableNativeCode=true`).
 *
 * NOTE: This only affects Fresco. None of the other libraries use native code.
 */
val Project.isNativeCodeEnabled: Boolean
    get() = System.getProperty("enableNativeCode", "false")!!.toBoolean()

private fun Project.intProperty(name: String): Int {
    return (property(name) as String).toInt()
}

private fun Project.stringProperty(name: String): String {
    return property(name) as String
}

private inline fun <T> List<T>.sumByIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}
