package fuurineditor.repository.data

import fuurineditor.service.data.scene.WorldLayer
import fuurineditor.service.data.scene.WorldScene
import fuurineditor.service.data.toTiletipFile
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import java.nio.file.Path

@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class SceneJson {
    @SerialName("name")
    abstract val name: String
}

@Serializable
@SerialName("world")
data class WorldSceneJson(

    @SerialName("name")
    override val name: String,

    @SerialName("width")
    val width: Int = 27,

    @SerialName("height")
    val height: Int = 15,

    @SerialName("layer")
    val layer: WorldLayerJson = WorldLayerJson(data = Array(width) { Array(height) { -1 } }),

    @SerialName("tiletip_mapping")
    val tiletipMapping: Map<Int, String> = mutableMapOf(0 to "kusa.json"),


    ) : SceneJson()


@Serializable
data class WorldLayerJson(

    @SerialName("data")
    val data: Array<Array<Int>>

)


fun WorldSceneJson.toWorldScene(tiletipBase: Path): WorldScene {
    val layer = WorldLayer(xSize = this@toWorldScene.width, ySize = this@toWorldScene.height)


    layer.rowData = this@toWorldScene.layer.data.map { layerX ->
        layerX.map { layerXY ->
            if (layerXY == -1) {
                null
            } else {
                if (tiletipMapping[layerXY] == null) {
                    null
                } else {
                    tiletipBase.resolve(tiletipMapping[layerXY]!!).toFile().toTiletipFile()
                }
            }
        }.toTypedArray()
    }.toTypedArray()
    return WorldScene(layer = layer)
}

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    @SerialName("name")
    override val name: String


) : SceneJson()

