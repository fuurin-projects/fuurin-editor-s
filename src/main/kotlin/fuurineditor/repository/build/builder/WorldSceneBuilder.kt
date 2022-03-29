package fuurineditor.repository.build.builder

import fuurineditor.repository.build.data.scene.SceneJson
import fuurineditor.repository.build.data.scene.WorldSceneJson
import fuurineditor.service.data.scene.WorldScene
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class WorldSceneBuilder {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(
        rowBasePath: Path,
        worldSceneList: List<WorldScene>
    ): List<SceneJson> {

        val sceneJsonList = mutableListOf<SceneJson>()

        for (worldScene in worldSceneList) {

            sceneJsonList += WorldSceneJson(name = worldScene.id.path, data = worldScene.id.path)

        }

        return sceneJsonList

    }

}