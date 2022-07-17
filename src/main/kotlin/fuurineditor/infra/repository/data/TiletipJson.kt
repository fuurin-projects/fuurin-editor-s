package fuurineditor.infra.repository.data

import fuurineditor.service.data.TiletipFileStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("normal_tiletip")
data class TiletipJson(

    @SerialName("display_name")
    val displayName: String = ""

) : IconBaseJson()

fun TiletipJson.toTiletipFileStatus(): TiletipFileStatus {
    return TiletipFileStatus(this.displayName)
}
