package fuurineditor.repository.build.builder

import fuurineditor.repository.SceneRepository
import fuurineditor.repository.WorldSceneRepository
import fuurineditor.repository.build.data.SceneMetaJson
import fuurineditor.repository.build.data.SpriteRowData
import fuurineditor.repository.data.SceneJson
import fuurineditor.repository.data.WorldSceneJson
import fuurineditor.repository.data.toWorldScene
import fuurineditor.repository.tiletip
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Component
class SceneBuilder(
    private val worldSceneBuilder: WorldSceneBuilder,
    private val sceneRepository: SceneRepository,
    private val worldSceneRepository: WorldSceneRepository
) {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(path: ProjectPath, rowBasePath: Path, spriteData: Map<String, List<SpriteRowData>>) {

        val sceneDirectoryPath = rowBasePath.resolve("./data/scene")
        if (sceneDirectoryPath.exists().not()) {
            Files.createDirectories(sceneDirectoryPath)
        }

        //Sceneの一覧を準備
        val allSceneList: List<SceneFile> = sceneRepository.getAllSceneList(path)

        //FileとJsonのペアを作成
        val allSceneJsonList: List<Pair<SceneFile, SceneJson>> = allSceneList.map {
            Pair(it, sceneRepository.loadScene(projectPath = path, it))
        }

        //WorldSceneJsonを生成
        val sceneJsonList = worldSceneBuilder.build(
            sceneDirectoryPath,
            allSceneJsonList
                .filter { it.second is WorldSceneJson }
                .map { (it.second as WorldSceneJson).toWorldScene(path.tiletip, it.first) }
        )

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