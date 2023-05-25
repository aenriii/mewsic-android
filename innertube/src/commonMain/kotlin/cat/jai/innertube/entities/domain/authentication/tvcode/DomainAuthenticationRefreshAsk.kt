package cat.jai.innertube.entities.domain.authentication.tvcode

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DomainAuthenticationRefreshAsk (
    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("client_id")
    val clientID: String,

    @SerialName("client_secret")
    val clientSecret: String,

    @SerialName("grant_type")
    val grantType: String = "refresh_token",
)
