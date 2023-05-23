package cat.jai.innertube.entities.authentication

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeAccountAuthenticationDetails(
    val deviceId: String,
    val expireTime: Long, // unix timestamp

    val accessToken: String,
    val refreshToken: String,
    val scope: String,
    val tokenType: String,
)
