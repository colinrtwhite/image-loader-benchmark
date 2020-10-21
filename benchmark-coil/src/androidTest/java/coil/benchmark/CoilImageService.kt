package coil.benchmark

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.OriginalSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class CoilImageService(private val context: Context) : ImageService {

    private val imageLoader = ImageLoader.Builder(context)
        .bitmapPoolingEnabled(false)
        .build()

    override fun execute(uri: Uri): Drawable = runBlocking {
        val request = ImageRequest.Builder(context)
            .data(uri)
            .size(OriginalSize)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .build()
        (imageLoader.execute(request) as SuccessResult).drawable
    }

    override fun shutdown() = runBlocking(Dispatchers.Main.immediate) {
        imageLoader.shutdown()
    }
}
