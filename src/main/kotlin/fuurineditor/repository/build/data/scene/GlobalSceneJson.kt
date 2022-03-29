package fuurineditor.repository.build.data.scene

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    @SerialName("test")
    val test: String

) : SceneJson(name = "global")