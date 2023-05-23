package cat.jai.innertube.authentication.entities

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeAccountAuthenticationDetails(
    val device_code: String,
    val expire_time: ULong, // unix timestamp

    val access_token: String,
    val refresh_token: String,
    val scope: String,
    val token_type: String,
)
