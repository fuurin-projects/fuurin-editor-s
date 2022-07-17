package fuurineditor.infra.repository.data

import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.event.Event
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.event.InputControllerKeyType
import fuurineditor.service.data.event.InputControllerNode
import fuurineditor.service.data.event.OutputEventStateNode
import fuurineditor.service.data.fromIndexKey
import fuurineditor.service.data.scene.GlobalScene
import fuurineditor.service.data.scene.WorldLayer
import fuurineditor.service.data.scene.WorldScene
import fuurineditor.service.data.toIndexKye
import fuurineditor.service.data.toTiletipFile
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import java.nio.file.Path
import java.util.*

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
    val width: Int = 27,

    @SerialName("event_list")
    val eventList: List<EventJson> = arrayListOf()

) : SceneJson()

fun GlobalSceneJson.toGlobalScene(sceneFile: SceneFile): GlobalScene {

    val eventList: List<Event> = this@toGlobalScene.eventList.map {

        val nodeList = mutableMapOf<UUID, Pair<EventNodeJson, EventNode>>()

        val event = Event(
            name = it.name,
            nodeList = it.nodeList.map nodeListMap@{ nodeJson ->

                val eventNode = when (nodeJson) {
                    is InputControllerNodeJson -> InputControllerNode(
                        id = UUID.fromString(nodeJson.id),
                        type = InputControllerKeyType.fromString(nodeJson.type),
                        offsetX = nodeJson.offsetX,
                        offsetY = nodeJson.offsetY,
                        width = nodeJson.width,
                        height = nodeJson.height
                    )
                    is OutputEventStateNodeJson -> OutputEventStateNode(
                        id = UUID.fromString(nodeJson.id),
                        eventState = nodeJson.eventState,
                        offsetX = nodeJson.offsetX,
                        offsetY = nodeJson.offsetY,
                        width = nodeJson.width,
                        height = nodeJson.height
                    )
                    else -> throw IllegalArgumentException("Not found NodeJson Type.")
                }
                nodeList[eventNode.id] = Pair(nodeJson, eventNode)

                return@nodeListMap eventNode


            }
        )

        //コネクター関係のリレーションを貼る
        nodeList.forEach { id, eventNodePair ->

            //左
            for (i in 0 until eventNodePair.second.leftConnector.size) {

                eventNodePair.second.leftConnector[i] += eventNodePair.first.leftConnector[i].map { idString ->
                    nodeList[UUID.fromString(idString)]!!.second
                }
            }

            //右
            for (i in 0 until eventNodePair.second.rightConnector.size) {
                eventNodePair.second.rightConnector[i] += eventNodePair.first.rightConnector[i].map { idString ->
                    nodeList[UUID.fromString(idString)]!!.second
                }
            }

        }

        return@map event

    }

    return GlobalScene(
        id = sceneFile.id,
        eventList = eventList
    )

}

fun GlobalScene.toGlobalSceneJson(): GlobalSceneJson {

    val eventList: List<EventJson> = this@toGlobalSceneJson.eventList.map {
        EventJson(
            name = it.name,
            nodeList = it.nodeList.map { node ->

                when (node) {
                    is InputControllerNode -> InputControllerNodeJson(
                        id = node.id.toString(),
                        type = node.type.type,
                        offsetX = node.offsetX,
                        offsetY = node.offsetY,
                        width = node.width,
                        height = node.height,
                        leftConnector = node.leftConnector.map { eventList ->
                            eventList.map { eventNode ->
                                eventNode.id.toString()
                            }.toMutableList()
                        }.toTypedArray(),
                        rightConnector = node.rightConnector.map { eventList ->
                            eventList.map { eventNode ->
                                eventNode.id.toString()
                            }.toMutableList()
                        }.toTypedArray()
                    )
                    is OutputEventStateNode -> OutputEventStateNodeJson(
                        id = node.id.toString(),
                        eventState = node.eventState,
                        offsetX = node.offsetX,
                        offsetY = node.offsetY,
                        width = node.width,
                        height = node.height,
                        leftConnector = node.leftConnector.map { eventList ->
                            eventList.map { eventNode ->
                                eventNode.id.toString()
                            }.toMutableList()
                        }.toTypedArray(),
                        rightConnector = node.rightConnector.map { eventList ->
                            eventList.map { eventNode ->
                                eventNode.id.toString()
                            }.toMutableList()
                        }.toTypedArray()
                    )
                    else -> throw IllegalArgumentException("Not found Node Type.")
                }

            }
        )
    }

    return GlobalSceneJson(
        eventList = eventList
    )

}

