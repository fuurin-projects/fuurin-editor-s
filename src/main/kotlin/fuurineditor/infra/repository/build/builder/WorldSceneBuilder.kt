package fuurineditor.infra.repository.build.builder

import fuurineditor.infra.repository.build.data.scene.GWorldSceneJson
import fuurineditor.infra.repository.build.data.scene.SceneJson
import fuurineditor.service.data.scene.WorldScene
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class WorldSceneBuilder {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(
        sceneDirectoryPath: Path,
        worldSceneList: List<WorldScene>
    ): List<GWorldSceneJson> {

        val sceneJsonList = mutableListOf<GWorldSceneJson>()

        for (worldScene in worldSceneList) {

            val worldSceneJson = GWorldSceneJson(name = worldScene.id.path, data = worldScene.id.path)
            sceneJsonList += worldSceneJson

            val filePath = sceneDirectoryPath.resolve("${worldScene.id.path}.json")

            filePath.toFile().writeText(
                json.encodeToString(
                    worldSceneJson as SceneJson
                )
            )

        }

        return sceneJsonList

    }

}