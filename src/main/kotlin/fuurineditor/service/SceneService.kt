package fuurineditor.service

import fuurineditor.repository.SceneRepository
import fuurineditor.service.data.File
import fuurineditor.ui.compose.window.RowScene
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class SceneService(
    private val sceneRepository: SceneRepository
) {

    fun getScene(projectPath: Path): Flow<File> {
        return sceneRepository.getScene(projectPath)
    }

    suspend fun addScene(projectPath: Path, rowScene: RowScene): Unit {
        return sceneRepository.addScene(projectPath, rowScene)
    }
}