package fuurineditor.service

import fuurineditor.repository.TiletipRepository
import fuurineditor.service.data.File
import fuurineditor.ui.compose.window.RowTileTip
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class TiletipService(
    private val tiletipRepository: TiletipRepository
) {

    fun getTiletip(): Flow<File> {
        return tiletipRepository.getTiletip()
    }

    suspend fun addTiletip(rowTileTip: RowTileTip): Unit {
        return tiletipRepository.addTiletip(rowTileTip)
    }

}