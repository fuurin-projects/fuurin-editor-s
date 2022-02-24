package fuurineditor.service

import fuurineditor.repository.TiletipRepository
import fuurineditor.service.data.File
import fuurineditor.ui.compose.window.RowTileTip
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class TiletipService(
    private val tiletipRepository: TiletipRepository
) {

    fun getTiletip(projectPath: Path): Flow<File> {
        return tiletipRepository.getTiletip(projectPath)
    }

    suspend fun addTiletip(projectPath: Path, rowTileTip: RowTileTip): Unit {
        return tiletipRepository.addTiletip(projectPath, rowTileTip)
    }

}