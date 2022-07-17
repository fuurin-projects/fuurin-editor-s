package fuurineditor.infra.build.data.scene

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    override val name: String = "global"

) : SceneJson()