package fuurineditor.infra.build.builder

import fuurineditor.infra.build.data.GWorldJson
import fuurineditor.infra.build.data.WorldMetaJson
import fuurineditor.infra.repository.data.toWorldSceneJson
import fuurineditor.app.service.data.fromIndexKey
import fuurineditor.app.service.data.scene.WorldScene
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

/**
 * WorldSceneで使うWorldの情報を生成するBuilder
 */
@Component
class WorldBuilder {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(
        rowBasePath: Path,
        worldSceneList: List<WorldScene>
    ): List<GWorldJson> {

        println("====================")
        println("Worldデータの生成を開始")
        println("====================")

        val worldDirectoryPath = rowBasePath.resolve("./data/world")
        if (worldDirectoryPath.exists().not()) {
            Files.createDirectories(worldDirectoryPath)
        }

        val worldJsonList = mutableListOf<GWorldJson>()

        for (worldScene in worldSceneList) {

            //TODO: もっとスマートにしたい
            //マッピングデータの変換
            val imageMapping: Map<String, String> = worldScene.toWorldSceneJson().tiletipMapping
                .mapKeys {
                    it.key.toString()
                }
                .mapValues {
                    fromIndexKey(it.value).path
                }


            val worldJson = GWorldJson(
                worldName = worldScene.id.path,
                imageData = worldScene.toWorldSceneJson().layer.data,
                imageMapping = imageMapping
            )
            worldJsonList += worldJson

            val filePath = worldDirectoryPath.resolve("${worldScene.id.path}.json")

            filePath.toFile().writeText(
                json.encodeToString(
                    worldJson
                )
            )

        }

        //Metaの生成
        val sceneRegistries: MutableMap<String, String> = mutableMapOf()

        for (worldJson in worldJsonList) {
            sceneRegistries[worldJson.worldName] = "/data/world/${worldJson.worldName}.json"
        }

        val metaJsonPath = worldDirectoryPath.resolve("meta.json")
        metaJsonPath.toFile().writeText(
            json.encodeToString(
                WorldMetaJson(worldRegistries = sceneRegistries)
            )
        )

        return worldJsonList
    }
}