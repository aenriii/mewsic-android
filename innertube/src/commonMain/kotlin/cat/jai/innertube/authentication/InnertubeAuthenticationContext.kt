package cat.jai.innertube.authentication

import cat.jai.innertube.entities.authentication.AuthenticationMessage
import cat.jai.innertube.entities.authentication.YoutubeAccountAuthenticationDetails
import cat.jai.innertube.entities.domain.authentication.tvcode.DomainAuthenticationRefreshAsk
import cat.jai.innertube.entities.domain.authentication.tvcode.DomainAuthenticationRefreshSuccess
import cat.jai.innertube.util.getHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.channels.Channel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

class InnertubeAuthenticationContext(
    val authenticationDetails: YoutubeAccountAuthenticationDetails,
    private val httpClient: HttpClient = getHttpClient()
) {
    suspend fun tryRefresh() {
        val now = Clock.System.now()
        if (now.toEpochMilliseconds() < authenticationDetails.expireTime) {
            return
        }

        val c = Channel<AuthenticationMessage>()
        val (clientId, clientSecret) = getClientDetails(httpClient, c)
        val refreshAsk = DomainAuthenticationRefreshAsk(
            refreshToken = authenticationDetails.refreshToken,
            clientID = clientId,
            clientSecret = clientSecret
        )
        while (true) {
            val res = httpClient.post {
                url("https://www.youtube.com/o/oauth2/token")
                setBody(refreshAsk)
            }
            try {
                val success = res.body<DomainAuthenticationRefreshSuccess>()
                authenticationDetails.accessToken = success.accessToken
                authenticationDetails.expireTime = Clock.System.now().plus(success.expiresIn, DateTimeUnit.SECOND).toEpochMilliseconds()
                break
            } catch (_: Exception) {

            }
        }
    }
    suspend fun HttpRequestBuilder.addAuthentication() {
        tryRefresh()
        header("Authorization", "Bearer ${authenticationDetails.accessToken}")
    }


    suspend operator fun invoke(block: suspend InnertubeAuthenticationContext.() -> Unit) {
        block(this)
    }
}