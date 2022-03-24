package fuurineditor.repository

import fuurineditor.repository.data.SceneJson
import fuurineditor.repository.data.WorldSceneJson
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneType
import fuurineditor.service.data.toSceneFile
import fuurineditor.ui.compose.window.RowScene
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Repository
class SceneRepository {

    fun getScene(projectPath: ProjectPath): Flow<File> {

        return flow<File> {
            val scenePath = projectPath.scene

            //シーンフォルダがなければ作成
            if (scenePath.exists().not()) {
                Files.createDirectories(scenePath)
                //return@flow
            }

            emit(scenePath.toFile().toSceneFile())

        }

    }

    private val json = Json { encodeDefaults = true }

    suspend fun addScene(projectPath: ProjectPath, rowScene: RowScene): Unit {

        val scenePath = projectPath.scene

        //タイルチップフォルダがなければ作成
        //シーンフォルダがなければ作成
        if (scenePath.exists().not()) {
            Files.createDirectories(scenePath)
        }

        val sceneJsonPath = scenePath.resolve("${rowScene.name}.json")

        try {

            val sceneJson: SceneJson = when (rowScene.type) {
                SceneType.WORLD -> {
                    WorldSceneJson(
                        //name = rowScene.name
                    )
                }
            }
            sceneJsonPath.toFile().writeText(
                json.encodeToString(
                    sceneJson
                )
            )

        } finally {

        }

    }

}

/**
 * Project内のsceneのパス
 */
val ProjectPath.scene: Path
    get() {
        return this@scene.path.resolve("src/main/scene")
    }