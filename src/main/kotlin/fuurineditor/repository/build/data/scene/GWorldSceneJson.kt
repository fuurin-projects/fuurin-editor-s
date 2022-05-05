package fuurineditor.repository.build.data.scene

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("world")
data class GWorldSceneJson(

    /**
     * Sceneの名前
     */
    override val name: String,

    /**
     * マップデータを格納している場所の名前
     */
    @SerialName("data")
    val data: String

) : SceneJson()
