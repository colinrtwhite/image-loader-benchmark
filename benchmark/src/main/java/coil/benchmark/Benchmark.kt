package coil.benchmark

import android.content.Context
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.core.net.toUri
import kotlin.test.assertEquals

// File containing the common benchmark code.

private val IMAGES = arrayOf(
    "image_1.jpg",
    "image_2.jpg",
    "image_3.jpg",
    "image_4.jpg",
    "image_5.jpg"
)

fun benchmarkFileUriGet(
    context: Context,
    rule: BenchmarkRule,
    service: ImageService
) = rule.measureRepeated {
    val uris = runWithTimingDisabled {
        createFileUris(context, *IMAGES)
    }
    uris.forEach { uri ->
        val drawable = service.execute(uri)
        runWithTimingDisabled {
            // Ensure the drawable was loaded at original size.
            assertEquals(1080, drawable.intrinsicWidth)
        }
    }
}

fun benchmarkNetworkUriGet(
    context: Context,
    rule: BenchmarkRule,
    service: ImageService
) = rule.measureRepeated {
    val mockWebServer = runWithTimingDisabled {
        createMockWebServer(context, *IMAGES)
    }
    mockWebServer.use { server ->
        IMAGES.forEach { image ->
            val drawable = service.execute(server.url(image).toString().toUri())
            runWithTimingDisabled {
                // Ensure the drawable was loaded at original size.
                assertEquals(1080, drawable.intrinsicWidth)
            }
        }
    }
}
