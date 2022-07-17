package fuurineditor.repository.build.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoaderJson(

    @SerialName("sprite_loader")
    private val spriteLoader: String = "/assets/sprite/meta.json",

    @SerialName("world_loader")
    private val worldLoader: String = "/data/world/meta.json",

    @SerialName("scene_loader")
    private val sceneLoader: String = "/data/scene/meta.json",

    @SerialName("icon_loader")
    private val iconLoader: String = "/data/icon/meta.json",

    @SerialName("event_loader")
    private val eventLoader: String = "/data/event/meta.json"


)
