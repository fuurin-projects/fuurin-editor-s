package fuurineditor.repository

import fuurineditor.service.data.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Repository
class TiletipRepository {

    fun getTiletip(projectPath: Path): Flow<File> {

        return flow<File> {
            val tiletipPath = projectPath.resolve("src/main/resources/assets/textures/tiletip")

            //タイルチップフォルダがなければ作成
            if (tiletipPath.exists().not()) {
                Files.createDirectories(tiletipPath)
                return@flow
            }


        }

    }

}