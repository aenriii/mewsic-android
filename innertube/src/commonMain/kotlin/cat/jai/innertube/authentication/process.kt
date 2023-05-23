package cat.jai.innertube.authentication

import cat.jai.innertube.entities.authentication.AuthenticationMessage
import cat.jai.innertube.entities.authentication.YoutubeAccountAuthenticationDetails
import cat.jai.innertube.entities.domain.tvcode.DomainTVCodeAsk
import cat.jai.innertube.entities.domain.tvcode.DomainTVCodeAuthenticationStatus
import cat.jai.innertube.entities.domain.tvcode.DomainTVCodeAuthenticationSuccess
import cat.jai.innertube.entities.domain.tvcode.DomainTVCodeRetreive
import cat.jai.innertube.util.RandUtils
import cat.jai.innertube.util.getHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import kotlin.concurrent.thread
import kotlin.time.Duration

val SCRIPT_URL_REGEX: Regex = Regex("""<script id="base-js" src="(.*?)" nonce=".*?"></script>""")
val CLIENT_ID_SECRET_REGEX: Regex = Regex("""clientId:"([-\w]+\.apps\.googleusercontent\.com)",\w+:"(\w+)""")

const val USER_AGENT: String = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
        "AppleWebKit/537.36 (KHTML, like Gecko) " +
        "Chrome/91.0.4472.124 " +
        "Safari/537.36"
/**
 * Launches the authentication process for Youtube
 * @return [ReceiveChannel] of [AuthenticationMessage] that will be sent to the caller
 */
fun launchAuthenticationProcess(callback: suspend (YoutubeAccountAuthenticationDetails) -> Unit = {}): ReceiveChannel<AuthenticationMessage> {
    val sendChannel = Channel<AuthenticationMessage>()
    val ret = object : ReceiveChannel<AuthenticationMessage> by sendChannel {
    }
    thread(start = true, isDaemon = true) { suspend {
        val client = getHttpClient()
        val (clientId, client_secret) = getClientDetails(client, sendChannel)
        val deviceId = RandUtils.randUuidString()
        var authenticated = false
        while (!authenticated) {
            val askForCode = DomainTVCodeAsk(
                clientID = clientId,
                deviceID = deviceId
            )
            val retrieveCode = client.post {
                url("https://www.youtube.com/o/oauth2/device/code")
                setBody(askForCode)
            }.body<DomainTVCodeRetreive>()

            val codeExpiresTime = Clock.System.now().plus(retrieveCode.expiresIn, DateTimeUnit.SECOND)
            sendChannel.send(AuthenticationMessage.postCode(retrieveCode.userCode))
            sendChannel.send(AuthenticationMessage.postTime(codeExpiresTime.toEpochMilliseconds()))

            while (Clock.System.now().toEpochMilliseconds() < codeExpiresTime.toEpochMilliseconds()) {

                delay(retrieveCode.interval * 1000)

                val casRequest = DomainTVCodeAuthenticationStatus(
                    code = retrieveCode.deviceCode,
                    clientID = clientId,
                    clientSecret = client_secret
                )
                val casResponse = client.post {
                    url("https://www.youtube.com/o/oauth2/token")
                    setBody(casRequest)
                }
                try {
                    val status = casResponse.body<DomainTVCodeAuthenticationSuccess>()

                    val authenticationDetails = YoutubeAccountAuthenticationDetails(
                        deviceId = deviceId,
                        expireTime = Clock.System.now().plus(status.expiresIn, DateTimeUnit.SECOND).toEpochMilliseconds(),
                        accessToken = status.accessToken,
                        refreshToken = status.refreshToken,
                        scope = status.scope,
                        tokenType = status.tokenType
                    )

                    // TODO: Store authentication details

                    callback(authenticationDetails)
                    authenticated = true
                    sendChannel.send(AuthenticationMessage.success())
                    break

                } catch (e: Exception) {
                    try {
                        println("Error: ${e.message}, ${casResponse.bodyAsText()}")
                    }
                    catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }

            }
        }

    }}
    return ret
}

private suspend fun getClientDetails(client: HttpClient, sendChannel: Channel<AuthenticationMessage>): Pair<String, String> {
    val res_yttv = client.get {
        url("https://www.youtube.com/tv")
        header("User-Agent", USER_AGENT)
    }
    val script_url = SCRIPT_URL_REGEX.find(res_yttv.bodyAsText())?.groupValues?.get(1)
    if (script_url == null) {
        sendChannel.send(AuthenticationMessage.failure("Could not find script url"))
        throw Exception("Could not find script url")
    }
    val res_script = client.get {
        url("$script_url")
        header("User-Agent", USER_AGENT)
    }
    val client_id_secret = CLIENT_ID_SECRET_REGEX.find(res_script.bodyAsText())?.groupValues
    if (client_id_secret == null) {
        sendChannel.send(AuthenticationMessage.failure("Could not find client id and secret"))
        throw Exception("Could not find client id and secret")
    }
    val client_id = client_id_secret[0]
    val client_secret = client_id_secret[1]
    return Pair(client_id, client_secret)
}
