package fuurineditor.repository

import fuurineditor.service.data.File
import fuurineditor.service.data.toTiletipFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Repository
class SceneRepository {

    fun getScene(projectPath: Path): Flow<File> {

        return flow<File> {
            val scenePath = projectPath.resolve("src/main/scene")

            //シーンフォルダがなければ作成
            if (scenePath.exists().not()) {
                Files.createDirectories(scenePath)
                //return@flow
            }

            emit(scenePath.toFile().toTiletipFile())

        }

    }

}