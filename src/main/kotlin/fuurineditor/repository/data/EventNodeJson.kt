package fuurineditor.repository.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class EventNodeJson() {
    /**
     * NodeのID
     */
    @SerialName("id")
    abstract val id: String
}

@Serializable
@SerialName("input.controller")
data class InputControllerNodeJson(

    @SerialName("id")
    override val id: String

) : EventNodeJson()
