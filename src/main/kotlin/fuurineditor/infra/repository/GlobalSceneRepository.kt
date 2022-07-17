package fuurineditor.infra.repository

import fuurineditor.property.IProjectProperty
import fuurineditor.infra.repository.data.EventNodeJson
import fuurineditor.infra.repository.data.GlobalSceneJson
import fuurineditor.infra.repository.data.SceneJson
import fuurineditor.infra.repository.data.toGlobalScene
import fuurineditor.infra.repository.data.toGlobalSceneJson
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.event.Event
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.scene.GlobalScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository

@Repository
class GlobalSceneRepository(
    private val projectProperty: IProjectProperty
) {


    private val json = Json {
        encodeDefaults = true;
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun loadGlobalScene(sceneFile: SceneFile): GlobalScene {

        val filePath = projectProperty.projectPath.scenePath.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val globalSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as GlobalSceneJson


        //TODO
        return globalSceneJson.toGlobalScene(
            sceneFile = sceneFile
        )

    }

    suspend fun saveGlobalScene(globalScene: GlobalScene) {

        val filePath = projectProperty.projectPath.scenePath.resolve("${globalScene.id.path}.json")

        filePath.toFile().writeText(
            json.encodeToString(
                globalScene.toGlobalSceneJson() as SceneJson
            )
        )

    }

    suspend fun connectEventNode(
        sceneFile: SceneFile,
        event: Event,
        from: EventNode,
        to: EventNode
    ) {

        val filePath = projectProperty.projectPath.scenePath.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val globalSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as GlobalSceneJson

        val eventJson = globalSceneJson.eventList
            .find { eventJson -> eventJson.name == event.name }

        val fromEventJson: EventNodeJson =
            eventJson!!.nodeList.find { eventNodeJson -> eventNodeJson.id == from.id.toString() }!!
        val toEventJson: EventNodeJson =
            eventJson!!.nodeList.find { eventNodeJson -> eventNodeJson.id == to.id.toString() }!!

        fromEventJson.rightConnector[0] += toEventJson.id
        toEventJson.leftConnector[0] += fromEventJson.id

        filePath.toFile().writeText(
            json.encodeToString(
                globalSceneJson as SceneJson
            )
        )

    }

}