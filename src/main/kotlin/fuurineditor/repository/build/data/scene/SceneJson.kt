package fuurineditor.repository.build.data.scene

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class SceneJson(

    /**
     * Sceneの名前
     */
    @SerialName("name")
    open val name: String,

    /**
     * Scene内のイベントリスト
     */
    @SerialName("event_list")
    val eventList: List<String> = mutableListOf(),

    /**
     * このSceneがゲーム起動時に一番はじめにロードされるべきか
     */
    @SerialName("data")
    val start: Boolean = false

)
