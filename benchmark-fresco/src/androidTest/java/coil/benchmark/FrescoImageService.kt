package coil.benchmark

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toDrawable
import com.facebook.datasource.DataSources
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import com.facebook.imagepipeline.image.CloseableBitmap
import com.facebook.imagepipeline.request.ImageRequestBuilder
import okhttp3.OkHttpClient

class FrescoImageService(private val context: Context) : ImageService {

    init {
        if (!Fresco.hasBeenInitialized()) {
            val config = OkHttpImagePipelineConfigFactory.newBuilder(context, OkHttpClient())
            if (!BuildConfig.ENABLE_NATIVE_CODE) {
                config.setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                config.setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                config.experiment().setNativeCodeDisabled(true)
            }
            Fresco.initialize(context, config.build())
        }
    }

    override fun execute(uri: Uri): Drawable {
        val request = ImageRequestBuilder
            .newBuilderWithSource(uri)
            .disableMemoryCache()
            .build()
        val dataSource = Fresco.getImagePipeline().fetchDecodedImage(request, null)
        return try {
            // Fresco frees the bitmap's memory after dataSource is closed,
            // however we don't need to return valid image data so return it anyways.
            // It wouldn't be fair to add the overhead of copying the bitmap to Fresco's benchmark.
            (DataSources.waitForFinalResult(dataSource)!!.get() as CloseableBitmap)
                .underlyingBitmap
                .toDrawable(context.resources)
        } finally {
            dataSource.close()
        }
    }

    override fun shutdown() {
        // We can't fully shutdown the singleton, but we clear as much as possible.
        Fresco.getImagePipeline().clearCaches()
    }
}
