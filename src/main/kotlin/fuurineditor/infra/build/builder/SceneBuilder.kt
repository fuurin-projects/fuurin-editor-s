package fuurineditor.infra.repository.build.builder

import fuurineditor.property.IProjectProperty
import fuurineditor.infra.repository.SceneRepository
import fuurineditor.infra.repository.WorldSceneRepository
import fuurineditor.infra.repository.build.data.SceneMetaJson
import fuurineditor.infra.repository.build.data.SpriteRowData
import fuurineditor.infra.repository.build.data.scene.GWorldSceneJson
import fuurineditor.infra.repository.data.SceneJson
import fuurineditor.infra.repository.data.WorldSceneJson
import fuurineditor.infra.repository.data.toWorldScene
import fuurineditor.infra.repository.tiletip
import fuurineditor.service.data.SceneFile
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Component
class SceneBuilder(
    private val projectProperty: IProjectProperty,
    private val worldSceneBuilder: WorldSceneBuilder,
    private val worldBuilder: WorldBuilder,
    private val sceneRepository: SceneRepository,
    private val worldSceneRepository: WorldSceneRepository
) {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(rowBasePath: Path, spriteData: Map<String, List<SpriteRowData>>) {

        val sceneDirectoryPath = rowBasePath.resolve("./data/scene")
        if (sceneDirectoryPath.exists().not()) {
            Files.createDirectories(sceneDirectoryPath)
        }

        //Sceneの一覧を準備
        val allSceneList: List<SceneFile> = sceneRepository.getAllSceneList()

        //FileとJsonのペアを作成
        val allSceneJsonList: List<Pair<SceneFile, SceneJson>> = allSceneList.map {
            Pair(it, sceneRepository.loadScene(it))
        }

        //WorldSceneJsonを生成
        val sceneJsonList: List<GWorldSceneJson> = worldSceneBuilder.build(
            sceneDirectoryPath,
            allSceneJsonList
                .filter { it.second is WorldSceneJson }
                .map { (it.second as WorldSceneJson).toWorldScene(projectProperty.projectPath.tiletip, it.first) }
        )

        //WorldJsonの生成
        val worldJsonList = worldBuilder.build(
            rowBasePath = rowBasePath,
            worldSceneList = allSceneJsonList
                .filter { it.second is WorldSceneJson }
                .map { (it.second as WorldSceneJson).toWorldScene(projectProperty.projectPath.tiletip, it.first) }
        )


        //Metaの生成
        val sceneRegistries: MutableMap<String, String> = mutableMapOf()

        for (sceneJson in sceneJsonList) {
            sceneRegistries[sceneJson.name] = "/data/scene/${sceneJson.name}.json"
        }

        val metaJsonPath = sceneDirectoryPath.resolve("meta.json")
        metaJsonPath.toFile().writeText(
            json.encodeToString(
                SceneMetaJson(sceneRegistries = sceneRegistries)
            )
        )
        println("Sceneデータのmeta情報生成生成. path=${metaJsonPath}")


    }

}