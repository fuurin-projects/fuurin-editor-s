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

        val sceneJsonString = sceneFile.path.toFile().readText()

        val worldSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as WorldSceneJson

        val worldScene = worldSceneJson.toWorldScene(projectPath.resolve("src/main/resources/assets/textures/tiletip"))
        worldScene.sceneFile = sceneFile
        return worldScene

    }

    suspend fun saveWorldScene(projectPath: Path, worldScene: WorldScene) {

        worldScene.sceneFile.path.toFile().writeText(
            json.encodeToString(
                worldScene.toWorldSceneJson() as SceneJson
            )
        )

    }

}