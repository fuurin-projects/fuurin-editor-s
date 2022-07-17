package fuurineditor.infra.repository

import fuurineditor.infra.build.builder.IconBuilder
import fuurineditor.infra.build.builder.LoaderBuilder
import fuurineditor.infra.build.builder.SceneBuilder
import fuurineditor.infra.build.builder.SpriteBuilder
import fuurineditor.infra.build.data.IconJson
import fuurineditor.infra.build.data.SpriteRowData
import fuurineditor.service.data.ProjectPath
import org.springframework.stereotype.Repository
import java.nio.file.Files
import kotlin.io.path.exists

@Repository
class WebBuildRepository(
    private val loaderBuilder: LoaderBuilder,
    private val tiletipRepository: TiletipRepository,
    private val spriteBuilder: SpriteBuilder,
    private val iconBuilder: IconBuilder,
    private val sceneBuilder: SceneBuilder
) {

    suspend fun build(path: ProjectPath) {

        val outputBase = path.path.resolve("./build")
        if (outputBase.exists().not()) {
            Files.createDirectories(outputBase)
        }

        val outputWebRowBase = outputBase.resolve("./web/row")
        if (outputWebRowBase.exists().not()) {
            Files.createDirectories(outputWebRowBase)
        }

        //loaderの出力
        loaderBuilder.build(outputWebRowBase)

        //タイルチップからスプライト画像の生成
        val allTiletipList = tiletipRepository.getAllTiletipList()
        var spriteNumber = 0
        val spriteNameBase = "sprite_"

        var spriteIndexX = 0
        var spriteIndexY = 0

        val spriteData = mutableMapOf<String, MutableList<SpriteRowData>>()

        for (tiletipFile in allTiletipList) {

            val spriteName = spriteNameBase + spriteNumber
            val iconJson = IconJson(
                sprite = spriteName,
                sx = spriteIndexX * 16,
                sy = spriteIndexY * 16,
                sw = 16,
                sh = 16
            )
            spriteIndexX++
            if (spriteIndexX >= 16) {
                spriteIndexX = 0
                spriteIndexY++
            }

            if (spriteData.containsKey(spriteName).not()) {
                spriteData[spriteName] = mutableListOf()
            }

            spriteData[spriteName]!!.add(
                SpriteRowData(
                    tiletipFile = tiletipFile,
                    iconJson = iconJson
                )
            )

        }

        spriteBuilder.build(outputWebRowBase, spriteData)

        iconBuilder.build(outputWebRowBase, spriteData)

        sceneBuilder.build(rowBasePath = outputWebRowBase, spriteData = spriteData)


    }

}