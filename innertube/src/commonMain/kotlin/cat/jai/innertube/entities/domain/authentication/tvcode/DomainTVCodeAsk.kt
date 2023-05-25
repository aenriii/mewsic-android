package cat.jai.innertube.entities.domain.authentication.tvcode

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class DomainTVCodeAsk (
    @SerialName("client_id")
    val clientID: String,

    @SerialName("device_id")
    val deviceID: String,

    @SerialName("model_name")
    val modelName: String = "ytlr::", // no idea what this means but iiwiw

    val scope: String = "http://gdata.youtube.com/ https://www.googleapis.com/auth/youtube-paid-content"
)
