package fuurineditor.repository

import fuurineditor.repository.data.GlobalSceneJson
import fuurineditor.repository.data.SceneJson
import fuurineditor.repository.data.toGlobalScene
import fuurineditor.repository.data.toGlobalSceneJson
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.GlobalScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository

@Repository
class GlobalSceneRepository {

    private val json = Json {
        encodeDefaults = true;
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun loadGlobalScene(projectPath: ProjectPath, sceneFile: SceneFile): GlobalScene {

        val filePath = projectPath.scene.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val globalSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as GlobalSceneJson


        //TODO
        return globalSceneJson.toGlobalScene(
            sceneFile = sceneFile
        )

    }

    suspend fun saveGlobalScene(projectPath: ProjectPath, globalScene: GlobalScene) {

        val filePath = projectPath.scene.resolve("${globalScene.id.path}.json")

        filePath.toFile().writeText(
            json.encodeToString(
                globalScene.toGlobalSceneJson() as SceneJson
            )
        )

    }

}