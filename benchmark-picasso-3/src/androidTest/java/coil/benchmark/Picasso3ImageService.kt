package coil.benchmark

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toDrawable
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class Picasso3ImageService(private val context: Context) : ImageService {

    private var picasso = Picasso.Builder(context).build()

    override fun execute(uri: Uri): Drawable {
        return picasso
            .load(uri)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .get()
            .toDrawable(context.resources)
    }

    override fun shutdown() {
        picasso.shutdown()
    }
}
