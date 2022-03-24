package fuurineditor.service

import fuurineditor.repository.WorldSceneRepository
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.WorldScene
import org.springframework.stereotype.Service

@Service
class WorldSceneService(
    private val worldSceneRepository: WorldSceneRepository
) {

    suspend fun loadWorldScene(projectPath: ProjectPath, sceneFile: SceneFile): WorldScene {

        return worldSceneRepository.loadWorldScene(projectPath = projectPath, sceneFile = sceneFile)

    }

    suspend fun saveWorldScene(projectPath: ProjectPath, worldScene: WorldScene) {
        worldSceneRepository.saveWorldScene(projectPath = projectPath, worldScene = worldScene)
    }

}