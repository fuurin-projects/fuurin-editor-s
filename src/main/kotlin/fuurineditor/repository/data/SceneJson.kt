package fuurineditor.repository.data

import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.fromIndexKey
import fuurineditor.service.data.scene.WorldLayer
import fuurineditor.service.data.scene.WorldScene
import fuurineditor.service.data.toIndexKye
import fuurineditor.service.data.toTiletipFile
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import java.nio.file.Path

@Serializable()
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
sealed class SceneJson {
    //@SerialName("name")
    //abstract val name: String
}

@Serializable
@SerialName("world")
data class WorldSceneJson(

    //@SerialName("name")
    //override val name: String,

    @SerialName("width")
    val width: Int = 27,

    @SerialName("height")
    val height: Int = 15,

    @SerialName("layer")
    val layer: WorldLayerJson = WorldLayerJson(data = Array(width) { Array(height) { -1 } }),

    @SerialName("tiletip_mapping")
    val tiletipMapping: Map<Int, String> = mutableMapOf(),


    ) : SceneJson()


@Serializable
data class WorldLayerJson(

    @SerialName("data")
    val data: Array<Array<Int>>

)


fun WorldSceneJson.toWorldScene(tiletipBase: Path, sceneFile: SceneFile): WorldScene {
    val layer = WorldLayer(xSize = this@toWorldScene.width, ySize = this@toWorldScene.height)

    layer.rowData = this@toWorldScene.layer.data.map { layerX ->
        layerX.map { layerXY ->
            if (layerXY == -1) {
                null
            } else {
                if (tiletipMapping[layerXY] == null) {
                    null
                } else {
                    val fileId = fromIndexKey(tiletipMapping[layerXY]!!)
                    tiletipBase.resolve("${fileId.path}.json").toFile().toTiletipFile()
                }
            }
        }.toTypedArray()
    }.toTypedArray()
    return WorldScene(
        id = sceneFile.id,
        width = this@toWorldScene.width,
        height = this@toWorldScene.height,
        layer = layer
    )
}

fun WorldScene.toWorldSceneJson(): WorldSceneJson {

    var mappingIndex = 0;
    val tiletipMapping: MutableMap<String, Int> = mutableMapOf()

    val data: Array<Array<Int>> = this@toWorldSceneJson.layer.rowData.map { layerX ->
        layerX.map { layerXYTiletipFile ->
            if (layerXYTiletipFile == null) {
                -1
            } else {
                if (tiletipMapping.containsKey(layerXYTiletipFile.id.toIndexKye())) {
                    tiletipMapping[layerXYTiletipFile.id.toIndexKye()] as Int
                } else {
                    mappingIndex++;
                    tiletipMapping[layerXYTiletipFile.id.toIndexKye()] = mappingIndex
                    mappingIndex
                }
            }
        }.toTypedArray()
    }.toTypedArray()

    val layer: WorldLayerJson = WorldLayerJson(data = data)

    return WorldSceneJson(
        width = this@toWorldSceneJson.width,
        height = this@toWorldSceneJson.height,
        layer = layer,
        tiletipMapping = tiletipMapping.entries.associate { entry -> entry.value to entry.key }
    )

}

@Serializable
@SerialName("global")
data class GlobalSceneJson(

    //@SerialName("name")
    //override val name: String

    @SerialName("width")
    val width: Int = 27


) : SceneJson()

