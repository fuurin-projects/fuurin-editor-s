package fuurineditor.repository.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class SceneJson {
    @SerialName("name")
    abstract val name: String
}

@Serializable
@SerialName("world")
data class WorldSceneJson(

    @SerialName("name")
    override val name: String,

    @SerialName("world")
    val world: String
) : SceneJson()

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    @SerialName("name")
    override val name: String


) : SceneJson()

