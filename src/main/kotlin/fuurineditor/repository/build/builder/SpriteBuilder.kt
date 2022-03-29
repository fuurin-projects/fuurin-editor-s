package fuurineditor.repository.build.builder

import fuurineditor.repository.build.data.SpriteMetaJson
import fuurineditor.repository.build.data.SpriteRowData
import fuurineditor.service.data.TiletipFile
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO
import kotlin.io.path.exists

/**
 * Sprite関係のビルド処理をおこなう
 */
@Component
class SpriteBuilder {

    private val imageWidth: Int = 256;
    private val imageHeight: Int = 256;

    private val json = Json { prettyPrint = true; encodeDefaults = true }

    suspend fun build(rowBasePath: Path, spriteData: Map<String, List<SpriteRowData>>) {

        val spriteDirectoryPath = rowBasePath.resolve("./assets/sprite")
        if (spriteDirectoryPath.exists().not()) {
            Files.createDirectories(spriteDirectoryPath)
        }

        val spriteRegistries: MutableMap<String, String> = mutableMapOf()

        println("====================")
        println("Sprite画像の生成を開始")
        println("====================")
        //Spriteのデータから画像を合成する
        for (entry in spriteData.entries) {
            val spriteName = entry.key
            val spriteRowDataList = entry.value

            val spriteImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

            val g = spriteImage.createGraphics()
            for (spriteRowData in spriteRowDataList) {
                println("path = ${spriteRowData.tiletipFile.file.rowFile}")
                val iconJson = spriteRowData.iconJson
                g.drawImage(
                    spriteRowData.tiletipFile.toBufferedImage(),
                    iconJson.sx,
                    iconJson.sy,
                    iconJson.sw,
                    iconJson.sh,
                    null
                )
            }

            g.dispose()

            val spriteFilePath = spriteDirectoryPath.resolve("${spriteName}.png").toFile()
            ImageIO.write(spriteImage, "PNG", spriteFilePath)
            println("Sprite画像の生成. path = ${spriteFilePath}")

            spriteRegistries[spriteName] = "/assets/sprite/${spriteName}.png"

        }


        val metaJsonPath = spriteDirectoryPath.resolve("meta.json")
        metaJsonPath.toFile().writeText(
            json.encodeToString(
                SpriteMetaJson(spriteRegistries)
            )
        )
        println("Spriteデータのmeta情報生成生成. path=${metaJsonPath}")


    }

}

fun TiletipFile.toBufferedImage(): BufferedImage {
    val imageFile = file.rowFile.toPath().resolve("../${file.rowFile.name.replace(".json", ".png")}").toFile()
    return ImageIO.read(imageFile)
}