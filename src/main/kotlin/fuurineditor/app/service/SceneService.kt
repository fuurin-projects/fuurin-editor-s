package fuurineditor.app.service

import fuurineditor.infra.repository.SceneRepository
import fuurineditor.app.service.data.File
import fuurineditor.app.service.data.SceneFile
import fuurineditor.ui.compose.window.RowScene
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class SceneService(
    private val sceneRepository: SceneRepository
) {

    fun getScene(): Flow<File> {
        return sceneRepository.getScene()
    }

    suspend fun addScene(rowScene: RowScene): Unit {
        return sceneRepository.addScene(rowScene)
    }

    suspend fun getGlobalFile(): SceneFile {
        return sceneRepository.getGlobalFile()
    }

}