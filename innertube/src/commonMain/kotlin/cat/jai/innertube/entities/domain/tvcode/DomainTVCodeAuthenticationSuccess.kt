package cat.jai.innertube.entities.domain.tvcode

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DomainTVCodeAuthenticationSuccess (
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("expires_in")
    val expiresIn: Long,

    @SerialName("refresh_token")
    val refreshToken: String,

    val scope: String,

    @SerialName("token_type")
    val tokenType: String
)
