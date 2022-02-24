package fuurineditor.repository.data

import fuurineditor.service.data.TiletipFileStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TiletipJson(

    @SerialName("display_name")
    val displayName: String = ""
)

fun TiletipJson.toTiletipFileStatus(): TiletipFileStatus {
    return TiletipFileStatus(this.displayName)
}
