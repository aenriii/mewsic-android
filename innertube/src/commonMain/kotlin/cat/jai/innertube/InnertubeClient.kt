package cat.jai.innertube

import cat.jai.innertube.authentication.InnertubeAuthenticationContext
import cat.jai.innertube.util.InnertubeConstants
import cat.jai.innertube.util.getHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class InnertubeClient(
    @PublishedApi internal val client: HttpClient = getHttpClient(),
    @PublishedApi internal val baseUrl: String = InnertubeConstants.Api.url,
    @PublishedApi internal val key: String = InnertubeConstants.Api.key,
    val clientName: String = InnertubeConstants.Client.Name.web,
    val clientVersion: String = InnertubeConstants.Client.Version.web,
    val platform: String = InnertubeConstants.Platform.web,
    val formFactor: String = InnertubeConstants.FormFactor.web,
    @PublishedApi internal val authenticationContext: InnertubeAuthenticationContext? = null
) {

    suspend fun get(url: String) {
        client.get {
            if (authenticationContext != null) {
                authenticationContext!! {
                    addAuthentication()
                }
                url("$baseUrl$url")
            } else {
                url("$baseUrl$url?key=$key")

            }
        }
    }

    suspend inline fun <reified T> post(url: String, data: T) {
        client.post {
            if (authenticationContext != null) {
                authenticationContext!! {
                    addAuthentication()
                }
                url("$baseUrl$url")
            } else {
                url("$baseUrl$url?key=$key")
            }
            setBody(data)
        }
    }

}