package coil.benchmark

import android.content.Context
import androidx.benchmark.junit4.BenchmarkRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Picasso3Benchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private lateinit var context: Context
    private lateinit var imageService: Picasso3ImageService

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        imageService = Picasso3ImageService(context)
    }

    @After
    fun after() {
        imageService.shutdown()
    }

    @Test
    fun fileUriGet() = benchmarkFileUriGet(context, benchmarkRule, imageService)

    @Test
    fun networkUriGet() = benchmarkNetworkUriGet(context, benchmarkRule, imageService)
}
