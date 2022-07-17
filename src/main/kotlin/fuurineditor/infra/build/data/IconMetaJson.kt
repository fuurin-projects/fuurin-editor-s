package fuurineditor.infra.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IconMetaJson(
    @SerialName("icon_registries")
    private val iconRegistries: Map<String, String> = mutableMapOf()
)
