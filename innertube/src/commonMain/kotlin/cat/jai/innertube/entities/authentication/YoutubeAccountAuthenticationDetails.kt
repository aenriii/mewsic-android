package cat.jai.innertube.entities.authentication

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeAccountAuthenticationDetails(
    var deviceId: String,
    var expireTime: Long, // unix timestamp

    var accessToken: String,
    var refreshToken: String,
    var scope: String,
    var tokenType: String,
)
