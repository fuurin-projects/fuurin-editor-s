package fuurineditor.service

import fuurineditor.repository.TiletipRepository
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectPath
import fuurineditor.ui.compose.window.RowTileTip
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class TiletipService(
    private val tiletipRepository: TiletipRepository
) {

    fun getTiletip(projectPath: ProjectPath): Flow<File> {
        return tiletipRepository.getTiletip(projectPath)
    }

    suspend fun addTiletip(projectPath: ProjectPath, rowTileTip: RowTileTip): Unit {
        return tiletipRepository.addTiletip(projectPath, rowTileTip)
    }

}