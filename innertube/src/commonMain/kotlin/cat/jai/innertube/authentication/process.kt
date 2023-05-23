package cat.jai.innertube.authentication

import cat.jai.innertube.authentication.entities.AuthenticationMessage
import cat.jai.innertube.authentication.entities.YoutubeAccountAuthenticationDetails
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelIterator
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.SelectClause1
import kotlin.concurrent.thread

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
fun launchAuthenticationProcess(callback: suspend (YoutubeAccountAuthenticationDetails) -> Unit): ReceiveChannel<AuthenticationMessage> {
    val sendChannel = Channel<AuthenticationMessage>()
    val ret = object : ReceiveChannel<AuthenticationMessage> {
        @DelicateCoroutinesApi
        override val isClosedForReceive: Boolean
            get() = sendChannel.isClosedForReceive

        @ExperimentalCoroutinesApi
        override val isEmpty: Boolean
            get() = sendChannel.isEmpty
        override val onReceive: SelectClause1<AuthenticationMessage>
            get() = sendChannel.onReceive
        override val onReceiveCatching: SelectClause1<ChannelResult<AuthenticationMessage>>
            get() = sendChannel.onReceiveCatching

        override fun cancel(cause: Throwable?): Boolean {
            sendChannel.cancel(CancellationException(cause?.message, cause))
            return true
        }

        override fun cancel(cause: CancellationException?) {
//            sendChannel.cancel(cause)
        }

        override fun iterator(): ChannelIterator<AuthenticationMessage> {
            return sendChannel.iterator()
        }

        override suspend fun receive(): AuthenticationMessage {
            return sendChannel.receive()
        }

        override suspend fun receiveCatching(): ChannelResult<AuthenticationMessage> {
            return sendChannel.receiveCatching()
        }

        override fun tryReceive(): ChannelResult<AuthenticationMessage> {
            return sendChannel.tryReceive()
        }

    }
    thread(start = true, isDaemon = true) { suspend {
        val client = HttpClient()
        val (client_id, client_secret) = getClientDetails(client, sendChannel)

        // todo: finish this

    }}
    return ret
}

suspend fun getClientDetails(client: HttpClient, sendChannel: Channel<AuthenticationMessage>): Pair<String, String> {
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
