package fuurineditor.service.data.scene

import fuurineditor.service.data.SceneFile

data class WorldScene(
    val width: Int,
    val height: Int,
    val layer: WorldLayer
) {

    lateinit var sceneFile: SceneFile

}