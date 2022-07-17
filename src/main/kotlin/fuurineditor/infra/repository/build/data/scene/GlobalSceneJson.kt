package fuurineditor.infra.repository.build.data.scene

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    override val name: String = "global"

) : SceneJson()