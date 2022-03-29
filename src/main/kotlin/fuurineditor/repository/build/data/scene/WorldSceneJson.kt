package fuurineditor.repository.build.data.scene

import kotlinx.serialization.SerialName

data class WorldSceneJson(

    /**
     * Sceneの名前
     */
    @SerialName("name")
    override val name: String,

    /**
     * マップデータを格納している場所の名前
     */
    @SerialName("data")
    val data: String

) : SceneJson(name = name)
