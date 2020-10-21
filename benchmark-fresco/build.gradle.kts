import coil.Library
import coil.compileSdk
import coil.isNativeCodeEnabled
import coil.isR8Enabled
import coil.minSdk
import coil.targetSdk
import coil.versionCode
import coil.versionName
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.benchmark")
}

if (project.isR8Enabled) {
    apply(plugin = "com.slack.keeper")
}

android {
    compileSdkVersion(project.compileSdk)
    defaultConfig {
        minSdkVersion(project.minSdk)
        targetSdkVersion(project.targetSdk)
        versionCode = project.versionCode
        versionName = project.versionName
        multiDexEnabled = true
        resConfigs("en")
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        vectorDrawables.useSupportLibrary = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "ENABLE_NATIVE_CODE", project.isNativeCodeEnabled.toString())
            isDebuggable = false
        }
        getByName("release") {
            buildConfigField("boolean", "ENABLE_NATIVE_CODE", project.isNativeCodeEnabled.toString())
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFile("$rootDir/shrinker-rules/main.pro")
            testProguardFile("$rootDir/shrinker-rules/test.pro")
        }
    }
    testBuildType = if (project.isR8Enabled) "release" else "debug"
}

dependencies {
    implementation(project(":benchmark"))
    androidTestImplementation(project(":benchmark"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(Library.ANDROIDX_CORE)

    implementation(Library.FRESCO) {
        if (!project.isNativeCodeEnabled) {
            exclude(group = "com.facebook.soloader", module = "soloader")
            exclude(group = "com.facebook.fresco", module = "soloader")
            exclude(group = "com.facebook.fresco", module = "nativeimagefilters")
            exclude(group = "com.facebook.fresco", module = "nativeimagetranscoder")
            exclude(group = "com.facebook.fresco", module = "memory-type-native")
            exclude(group = "com.facebook.fresco", module = "imagepipeline-native")
        }
    }
    implementation(Library.FRESCO_OKHTTP)

    implementation(Library.OKHTTP)
    implementation(Library.OKIO)
}
