package coil.benchmark

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.buffer
import okio.sink
import okio.source
import java.io.File

internal fun createMockWebServer(context: Context, vararg images: String): MockWebServer {
    return MockWebServer().apply {
        images.forEach { image ->
            val buffer = Buffer()
            context.assets.open(image).source().buffer().readAll(buffer)
            enqueue(MockResponse().setBody(buffer))
        }
        start()
    }
}

internal fun createFileUris(context: Context, vararg images: String): List<Uri> {
    val cacheDir = context.cacheDir.apply {
        deleteRecursively()
        mkdirs()
    }
    return images.map { image ->
        val file = File(cacheDir, image)
        file.sink().buffer().writeAll(context.assets.open(image).source())
        file.toUri()
    }
}
