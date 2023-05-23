package cat.jai.innertube.entities.domain.tvcode

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DomainTVCodeRetreive (
    @SerialName("device_code")
    val deviceCode: String,

    @SerialName("user_code")
    val userCode: String,

    @SerialName("expires_in")
    val expiresIn: Long,

    val interval: Long,

    @SerialName("verification_url")
    val verificationURL: String
)
