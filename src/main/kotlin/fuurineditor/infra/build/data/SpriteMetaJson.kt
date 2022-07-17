package fuurineditor.infra.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpriteMetaJson(

    @SerialName("sprite_registries")
    private val spriteRegistries: Map<String, String> = mutableMapOf()

)
