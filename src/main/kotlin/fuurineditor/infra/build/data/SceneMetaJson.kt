package fuurineditor.infra.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SceneMetaJson(

    @SerialName("scene_registries")
    private val sceneRegistries: Map<String, String> = mutableMapOf()

)
