package cat.jai.innertube.util

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.BrowserUserAgent
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    useArrayPolymorphism = true
}

expect val defaultFactory: HttpClientEngineFactory<*>;
fun getHttpClient(
    engineFactory: HttpClientEngineFactory<*> = defaultFactory,
    cookies: CookiesStorage = AcceptAllCookiesStorage(),
    builder: HttpClientConfig<*>.() -> Unit = {}
): HttpClient {
    return HttpClient(engineFactory) {
        BrowserUserAgent()

        install(ContentNegotiation) {
            json(json)
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(5)
            exponentialDelay()
        }

        install(ContentEncoding) {
            deflate()
            gzip()

        }
        install(HttpCookies) {
            storage = cookies
        }

        install(HttpCache)

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
        builder()
    }
}