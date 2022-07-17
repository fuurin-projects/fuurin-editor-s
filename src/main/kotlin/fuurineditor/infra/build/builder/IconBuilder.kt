package fuurineditor.infra.repository.build.builder

import fuurineditor.infra.repository.build.data.IconMetaJson
import fuurineditor.infra.repository.build.data.SpriteRowData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Component
class IconBuilder {

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(rowBasePath: Path, spriteData: Map<String, List<SpriteRowData>>) {

        val iconDirectoryPath = rowBasePath.resolve("./data/icon")
        if (iconDirectoryPath.exists().not()) {
            Files.createDirectories(iconDirectoryPath)
        }

        val iconRegistries: MutableMap<String, String> = mutableMapOf()

        println("====================")
        println("Iconデータの生成を開始")
        println("====================")

        for (entry in spriteData.entries) {
            val spriteRowDataList = entry.value

            for (spriteRowData in spriteRowDataList) {

                val iconJsonPath = iconDirectoryPath.resolve("${spriteRowData.tiletipFile.id.path}.json")
                iconJsonPath.toFile().writeText(
                    json.encodeToString(
                        spriteRowData.iconJson
                    )
                )

                iconRegistries[spriteRowData.tiletipFile.id.path] =
                    "/data/icon/${spriteRowData.tiletipFile.id.path}.json"

                println("Iconデータの生成. path=${iconJsonPath}")

            }
        }

        val metaJsonPath = iconDirectoryPath.resolve("meta.json")
        metaJsonPath.toFile().writeText(
            json.encodeToString(
                IconMetaJson(iconRegistries)
            )
        )
        println("Iconデータのmeta情報生成生成. path=${metaJsonPath}")


    }

}