package fuurineditor.service.data.scene

import fuurineditor.service.data.FileId

data class WorldScene(
    override val id: FileId,
    val width: Int,
    val height: Int,
    val layer: WorldLayer
) : Scene