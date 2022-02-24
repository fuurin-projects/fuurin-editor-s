package fuurineditor.repository

import fuurineditor.repository.data.TiletipJson
import fuurineditor.service.data.File
import fuurineditor.service.data.toTiletipFile
import fuurineditor.ui.compose.window.RowTileTip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

            emit(tiletipPath.toFile().toTiletipFile())

        }

    }

    suspend fun addTiletip(projectPath: Path, rowTileTip: RowTileTip): Unit = withContext(Dispatchers.IO) {

        val tiletipPath = projectPath.resolve("src/main/resources/assets/textures/tiletip")

        //タイルチップフォルダがなければ作成
        if (tiletipPath.exists().not()) {
            Files.createDirectories(tiletipPath)
        }

        val to = tiletipPath.resolve("${rowTileTip.name}.png")
        val from = rowTileTip.location

        try {
            Files.copy(from, to)
            tiletipPath.resolve("${rowTileTip.name}.json").toFile().writeText(
                Json.encodeToString(
                    TiletipJson(
                        displayName = rowTileTip.displayName
                    )
                )
            )

        } finally {

        }


    }

}