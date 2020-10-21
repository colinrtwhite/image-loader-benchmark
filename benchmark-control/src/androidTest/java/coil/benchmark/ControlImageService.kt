package coil.benchmark

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toFile
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.source

class ControlImageService(private val context: Context) : ImageService {

	private val okHttpClient = OkHttpClient()

    override fun execute(uri: Uri): Drawable {
		val inputStream = when (uri.scheme) {
			"file" -> {
				uri.toFile().source().buffer().inputStream()
			}
			"http", "https" -> {
				val request = Request.Builder()
					.url(uri.toString())
					.build()
				val response = okHttpClient.newCall(request).execute()
				response.body!!.byteStream()
			}
			else -> throw IllegalStateException()
		}
		return BitmapFactory.decodeStream(inputStream).toDrawable(context.resources)
	}

    override fun shutdown() {}
}
