package fuurineditor.repository

import fuurineditor.repository.data.GlobalSceneJson
import fuurineditor.repository.data.SceneJson
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.GlobalScene
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository

@Repository
class GlobalSceneRepository {

    private val json = Json {
        encodeDefaults = true;
        prettyPrint = true
    }

    suspend fun loadGlobalScene(projectPath: ProjectPath, sceneFile: SceneFile): GlobalScene {

        val filePath = projectPath.scene.resolve("${sceneFile.id.path}.json")

        val sceneJsonString = filePath.toFile().readText()

        val globalSceneJson = json.decodeFromString<SceneJson>(sceneJsonString) as GlobalSceneJson

        //TODO
        return GlobalScene(id = sceneFile.id)
    }

    suspend fun saveGlobalScene(projectPath: ProjectPath, globalScene: GlobalScene) {

    }

}