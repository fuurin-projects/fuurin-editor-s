package fuurineditor.service

import fuurineditor.repository.GlobalSceneRepository
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.event.Event
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.scene.GlobalScene
import org.springframework.stereotype.Service

@Service
class GlobalSceneService(
    private val globalSceneRepository: GlobalSceneRepository
) {

    suspend fun loadGlobalScene(projectPath: ProjectPath, sceneFile: SceneFile): GlobalScene {

        return globalSceneRepository.loadGlobalScene(projectPath = projectPath, sceneFile = sceneFile)

    }

    suspend fun saveGlobalScene(projectPath: ProjectPath, globalScene: GlobalScene) {
        globalSceneRepository.saveGlobalScene(projectPath = projectPath, globalScene = globalScene)
    }

    suspend fun connectEventNode(
        projectPath: ProjectPath,
        sceneFile: SceneFile,
        event: Event,
        from: EventNode,
        to: EventNode
    ) {
        globalSceneRepository.connectEventNode(
            projectPath = projectPath,
            sceneFile = sceneFile,
            event = event,
            from = from,
            to = to
        )
    }

}