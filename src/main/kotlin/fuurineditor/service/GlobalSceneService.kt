package fuurineditor.service

import fuurineditor.repository.GlobalSceneRepository
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.event.Event
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.scene.GlobalScene
import org.springframework.stereotype.Service

@Service
class GlobalSceneService(
    private val globalSceneRepository: GlobalSceneRepository
) {

    suspend fun loadGlobalScene(sceneFile: SceneFile): GlobalScene {

        return globalSceneRepository.loadGlobalScene(sceneFile = sceneFile)

    }

    suspend fun saveGlobalScene(globalScene: GlobalScene) {
        globalSceneRepository.saveGlobalScene(globalScene = globalScene)
    }

    suspend fun connectEventNode(
        sceneFile: SceneFile,
        event: Event,
        from: EventNode,
        to: EventNode
    ) {
        globalSceneRepository.connectEventNode(
            sceneFile = sceneFile,
            event = event,
            from = from,
            to = to
        )
    }

}