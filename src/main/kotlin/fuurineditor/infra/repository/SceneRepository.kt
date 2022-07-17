package fuurineditor.infra.repository

import fuurineditor.property.IProjectProperty
import fuurineditor.infra.repository.data.GlobalSceneJson
import fuurineditor.infra.repository.data.SceneJson
import fuurineditor.infra.repository.data.WorldSceneJson
import fuurineditor.service.data.File
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.SceneType
import fuurineditor.service.data.toSceneFile
import fuurineditor.ui.compose.window.RowScene
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Files
import kotlin.io.path.exists

@Repository
class SceneRepository(
    private val projectProperty: IProjectProperty
) {


    fun getScene(): Flow<File> {

        return flow<File> {
            val scenePath = projectProperty.projectPath.scenePath

            //シーンフォルダがなければ作成
            if (scenePath.exists().not()) {
                Files.createDirectories(scenePath)
                //return@flow
            }

            emit(scenePath.toFile().toSceneFile())

        }

    }


    private val json = Json { encodeDefaults = true }

    suspend fun addScene(rowScene: RowScene): Unit {

        val scenePath = projectProperty.projectPath.scenePath

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
                SceneType.GLOBAL -> {
                    GlobalSceneJson(
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


    suspend fun getAllSceneList(): List<SceneFile> {

        val scenePath = projectProperty.projectPath.scenePath

        //タイルチップフォルダがなければ作成
        if (scenePath.exists().not()) {
            Files.createDirectories(scenePath)
            return@getAllSceneList mutableListOf()
        }

        val sceneList = mutableListOf<SceneFile>()
        addSceneFile(scenePath.toFile().toSceneFile(), sceneList)

        println(sceneList[0].path)

        return sceneList
    }

    private suspend fun addSceneFile(root: SceneFile, list: MutableList<SceneFile>) {

        if (root.isDirectory.not()) {
            list += root
        } else {
            root.children.forEach {
                addSceneFile(it, list)
            }
        }

    }

    suspend fun loadScene(sceneFile: SceneFile): SceneJson {

        val filePath = projectProperty.projectPath.scenePath.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        return json.decodeFromString<SceneJson>(sceneJsonString)

    }

    suspend fun getGlobalFile(): SceneFile {

        val scenePath = projectProperty.projectPath.scenePath

        //シーンフォルダがなければ作成
        if (scenePath.exists().not()) {
            Files.createDirectories(scenePath)
        }

        //Globalのファイルがない場合は作成する
        if (scenePath.resolve("./global.json").exists().not()) {
            addScene(
                RowScene(
                    name = "global", type = SceneType.GLOBAL
                )
            )
        }

        return scenePath.resolve("./global.json").toFile().toSceneFile()


    }

}
