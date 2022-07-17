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

    @SerialName("width")
    abstract var width: Float

    @SerialName("height")
    abstract var height: Float

    @SerialName("left_connector")
    abstract val leftConnector: Array<MutableList<String>>

    @SerialName("right_connector")
    abstract val rightConnector: Array<MutableList<String>>
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

    @SerialName("width")
    override var width: Float,

    @SerialName("height")
    override var height: Float,

    @SerialName("input_type")
    val type: String,

    @SerialName("left_connector")
    override val leftConnector: Array<MutableList<String>> = arrayOf(),

    @SerialName("right_connector")
    override val rightConnector: Array<MutableList<String>> = arrayOf()

) : EventNodeJson()

@Serializable
@SerialName("output.event_state")
data class OutputEventStateNodeJson(

    @SerialName("id")
    override val id: String,

    @SerialName("offset_x")
    override var offsetX: Float,

    @SerialName("offset_y")
    override var offsetY: Float,

    @SerialName("width")
    override var width: Float,

    @SerialName("height")
    override var height: Float,

    @SerialName("event_state")
    val eventState: String,

    @SerialName("left_connector")
    override val leftConnector: Array<MutableList<String>> = arrayOf(),

    @SerialName("right_connector")
    override val rightConnector: Array<MutableList<String>> = arrayOf()

) : EventNodeJson()