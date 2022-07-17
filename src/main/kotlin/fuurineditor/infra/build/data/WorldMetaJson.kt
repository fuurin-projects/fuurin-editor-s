package fuurineditor.infra.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorldMetaJson(

    @SerialName("world_registries")
    private val worldRegistries: Map<String, String> = mutableMapOf()

)