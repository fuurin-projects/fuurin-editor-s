package fuurineditor.app.service

import fuurineditor.infra.repository.WorldSceneRepository
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.scene.WorldScene
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