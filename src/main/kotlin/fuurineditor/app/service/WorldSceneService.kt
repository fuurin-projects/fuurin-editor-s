package fuurineditor.service

import fuurineditor.infra.repository.WorldSceneRepository
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.scene.WorldScene
import org.springframework.stereotype.Service

@Service
class WorldSceneService(
    private val worldSceneRepository: WorldSceneRepository
) {

    suspend fun loadWorldScene(sceneFile: SceneFile): WorldScene {

        return worldSceneRepository.loadWorldScene(sceneFile = sceneFile)

    }

    suspend fun saveWorldScene(worldScene: WorldScene) {
        worldSceneRepository.saveWorldScene(worldScene = worldScene)
    }

}