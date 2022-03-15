package fuurineditor.repository

import fuurineditor.repository.data.SceneJson
import fuurineditor.repository.data.WorldSceneJson
import fuurineditor.repository.data.toWorldScene
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.WorldScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class WorldSceneRepository {

    suspend fun loadWorldScene(projectPath: Path, sceneFile: SceneFile): WorldScene {

        val sceneJsonString = sceneFile.path.toFile().readText()

        val worldSceneJson = Json.decodeFromString<SceneJson>(sceneJsonString) as WorldSceneJson

        return worldSceneJson.toWorldScene(projectPath.resolve("src/main/resources/assets/textures/tiletip"))

    }

}