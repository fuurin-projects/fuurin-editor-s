package fuurineditor.service

import fuurineditor.repository.WorldSceneRepository
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.WorldScene
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class WorldSceneService(
    private val worldSceneRepository: WorldSceneRepository
) {

    suspend fun loadWorldScene(projectPath: Path, sceneFile: SceneFile): WorldScene {

        return worldSceneRepository.loadWorldScene(projectPath = projectPath, sceneFile = sceneFile)

    }

    suspend fun saveWorldScene(projectPath: Path, worldScene: WorldScene) {
        worldSceneRepository.saveWorldScene(projectPath = projectPath, worldScene = worldScene)
    }

}