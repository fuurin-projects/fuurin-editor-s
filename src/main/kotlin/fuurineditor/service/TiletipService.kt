package fuurineditor.service

import fuurineditor.repository.TiletipRepository
import fuurineditor.service.data.File
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

}