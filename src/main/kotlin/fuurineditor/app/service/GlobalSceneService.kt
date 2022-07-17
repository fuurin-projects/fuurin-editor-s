package fuurineditor.app.service

import fuurineditor.infra.repository.GlobalSceneRepository
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.event.Event
import fuurineditor.app.service.data.event.EventNode
import fuurineditor.app.service.data.scene.GlobalScene
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