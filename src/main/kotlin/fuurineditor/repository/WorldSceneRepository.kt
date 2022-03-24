package fuurineditor.repository

import fuurineditor.repository.data.SceneJson
import fuurineditor.repository.data.WorldSceneJson
import fuurineditor.repository.data.toWorldScene
import fuurineditor.repository.data.toWorldSceneJson
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.WorldScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class WorldSceneRepository {

    private val json = Json {
        encodeDefaults = true;
        prettyPrint = true
    }

    suspend fun loadWorldScene(projectPath: Path, sceneFile: SceneFile): WorldScene {


        val filePath = projectPath.resolve("src/main/scene").resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val worldSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as WorldSceneJson

        val worldScene = worldSceneJson.toWorldScene(
            projectPath.resolve("src/main/resources/assets/textures/tiletip"),
            sceneFile
        )
        return worldScene

    }

    suspend fun saveWorldScene(projectPath: Path, worldScene: WorldScene) {

        val filePath = projectPath.resolve("src/main/scene").resolve("${worldScene.id.path}.json")

        filePath.toFile().writeText(
            json.encodeToString(
                worldScene.toWorldSceneJson() as SceneJson
            )
        )

    }

}