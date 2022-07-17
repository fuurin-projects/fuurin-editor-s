package fuurineditor.infra.repository

import fuurineditor.app.property.IProjectProperty
import fuurineditor.infra.repository.data.SceneJson
import fuurineditor.infra.repository.data.WorldSceneJson
import fuurineditor.infra.repository.data.toWorldScene
import fuurineditor.infra.repository.data.toWorldSceneJson
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.scene.WorldScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository

@Repository
class WorldSceneRepository(
    private val projectProperty: IProjectProperty
) {


    private val json = Json {
        encodeDefaults = true;
        prettyPrint = true
    }

    suspend fun loadWorldScene(sceneFile: SceneFile): WorldScene {

        val filePath = projectProperty.projectPath.scenePath.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val worldSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as WorldSceneJson

        val worldScene = worldSceneJson.toWorldScene(
            projectProperty.projectPath.tiletip,
            sceneFile
        )
        return worldScene

    }

    suspend fun saveWorldScene(worldScene: WorldScene) {

        val filePath = projectProperty.projectPath.scenePath.resolve("${worldScene.id.path}.json")

        filePath.toFile().writeText(
            json.encodeToString(
                worldScene.toWorldSceneJson() as SceneJson
            )
        )

    }

}