package fuurineditor.service

import fuurineditor.repository.SceneRepository
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.ui.compose.window.RowScene
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class SceneService(
    private val sceneRepository: SceneRepository
) {

    fun getScene(projectPath: ProjectPath): Flow<File> {
        return sceneRepository.getScene(projectPath)
    }

    suspend fun addScene(projectPath: ProjectPath, rowScene: RowScene): Unit {
        return sceneRepository.addScene(projectPath, rowScene)
    }

    suspend fun getGlobalFile(projectPath: ProjectPath): SceneFile {
        return sceneRepository.getGlobalFile(projectPath = projectPath)
    }

}