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

    @SerialName("offset_x")
    abstract var offsetX: Float

    @SerialName("offset_y")
    abstract var offsetY: Float
}

@Serializable
@SerialName("input.controller")
data class InputControllerNodeJson(

    @SerialName("id")
    override val id: String,

    @SerialName("offset_x")
    override var offsetX: Float,

    @SerialName("offset_y")
    override var offsetY: Float,

    @SerialName("input_type")
    val type: String

) : EventNodeJson()
