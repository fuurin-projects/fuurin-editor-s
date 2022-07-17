package fuurineditor.infra.repository.build.data.scene

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class SceneJson(

    /**
     * Scene内のイベントリスト
     */
    @SerialName("event_list")
    val eventList: List<String> = mutableListOf()


) {

    /**
     * Sceneの名前
     */
    @SerialName("name")
    abstract val name: String

    /**
     * このSceneがゲーム起動時に一番はじめにロードされるべきか
     */
    @SerialName("start")
    val start: Boolean = false

}
