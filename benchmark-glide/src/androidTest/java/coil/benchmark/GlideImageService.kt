package coil.benchmark

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import java.util.concurrent.CountDownLatch

class GlideImageService(private val context: Context) : ImageService {

    init {
        // OkHttpLibraryGlideModule will be detected automatically using manifest parsing.
        Glide.get(context)
    }

    override fun execute(uri: Uri): Drawable {
        return Glide.with(context)
            .load(uri)
            .skipMemoryCache(true)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()
    }

    override fun shutdown() {
        // Call clearMemory() from the main thread and block until the method completes.
        // We intentionally want to avoid importing coroutines to accomplish this as it
        // could allocate some static resources, which could affect the integrity of the benchmark.
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            // We can't fully shutdown the singleton, but we clear as much as possible.
            Glide.get(context).clearMemory()
            latch.countDown()
        }
        latch.await()
    }
}
