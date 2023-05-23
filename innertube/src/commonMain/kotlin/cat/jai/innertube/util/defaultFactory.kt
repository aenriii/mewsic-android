package cat.jai.innertube.util

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.OkHttp

actual val defaultFactory: HttpClientEngineFactory<*>
    get() = OkHttp