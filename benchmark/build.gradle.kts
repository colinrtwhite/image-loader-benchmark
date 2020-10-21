import coil.Library
import coil.compileSdk
import coil.minSdk
import coil.targetSdk
import coil.versionCode
import coil.versionName
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
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
    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = false }
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(Library.ANDROIDX_CORE)
    implementation(Library.ANDROIDX_MULTIDEX)

    implementation(Library.OKHTTP)
    implementation(Library.OKIO)

    api(kotlin("test-junit", KotlinCompilerVersion.VERSION))
    api(Library.JUNIT)

    api(Library.ANDROIDX_BENCHMARK)
    api(Library.ANDROIDX_TEST_CORE)
    api(Library.ANDROIDX_TEST_ESPRESSO_CORE)
    api(Library.ANDROIDX_TEST_JUNIT)
    api(Library.ANDROIDX_TEST_RULES)
    api(Library.ANDROIDX_TEST_RUNNER)

    api(Library.OKHTTP_MOCK_WEB_SERVER)
}
