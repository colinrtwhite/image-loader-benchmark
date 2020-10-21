package coil.benchmark

import android.graphics.drawable.Drawable
import android.net.Uri

interface ImageService {

    /** Synchronously fetch and decode the image data from [uri]. */
    fun execute(uri: Uri): Drawable

    /** Tear down. */
    fun shutdown()
}
