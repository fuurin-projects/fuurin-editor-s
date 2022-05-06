package fuurineditor.repository.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventJson(
    @SerialName("name")
    val name: String,

    @SerialName("node_list")
    val nodeList: List<EventNodeJson>

)
