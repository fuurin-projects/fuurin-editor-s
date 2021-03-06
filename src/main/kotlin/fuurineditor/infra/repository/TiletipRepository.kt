package fuurineditor.infra.repository

import fuurineditor.app.property.IProjectProperty
import fuurineditor.infra.repository.data.IconBaseJson
import fuurineditor.infra.repository.data.TiletipJson
import fuurineditor.app.service.data.File
import fuurineditor.app.service.data.TiletipFile
import fuurineditor.app.service.data.toTiletipFile
import fuurineditor.presen.ui.compose.window.RowTileTip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Files
import kotlin.io.path.exists

@Repository
class TiletipRepository(
    private val projectProperty: IProjectProperty
) {

    fun getTiletip(): Flow<File> {

        return flow<File> {
            val tiletipPath = projectProperty.projectPath.tiletip

            //タイルチップフォルダがなければ作成
            if (tiletipPath.exists().not()) {
                Files.createDirectories(tiletipPath)
                return@flow
            }

            emit(tiletipPath.toFile().toTiletipFile())

        }

    }

    suspend fun addTiletip(rowTileTip: RowTileTip): Unit = withContext(Dispatchers.IO) {

        val tiletipPath = projectProperty.projectPath.tiletip

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
                    ) as IconBaseJson
                )
            )

        } finally {

        }


    }

    suspend fun getAllTiletipList(): List<TiletipFile> {

        val tiletipPath = projectProperty.projectPath.tiletip

        //タイルチップフォルダがなければ作成
        if (tiletipPath.exists().not()) {
            Files.createDirectories(tiletipPath)
            return@getAllTiletipList mutableListOf()
        }

        val tileTipList = mutableListOf<TiletipFile>()
        addTiletipFile(tiletipPath.toFile().toTiletipFile(), tileTipList)

        println(tileTipList[0].file.rowFile)

        return tileTipList
    }

    private suspend fun addTiletipFile(root: TiletipFile, list: MutableList<TiletipFile>) {

        if (root.isDirectory.not()) {
            list += root
        } else {
            root.children.forEach {
                addTiletipFile(it, list)
            }
        }

    }


}
