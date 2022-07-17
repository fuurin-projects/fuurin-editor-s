package fuurineditor.infra.repository

import java.nio.file.Path

/**
 * プロジェクトルートからScene保存場所までのパス
 */
val Path.scenePath: Path
    get() {
        return this@scenePath.resolve("src/main/scene")
    }

/**
 * プロジェクトルートからTiletip保存場所までのパス
 */
val Path.tiletip: Path
    get() {
        return this@tiletip.resolve("src/main/resources/assets/textures/tiletip")
    }