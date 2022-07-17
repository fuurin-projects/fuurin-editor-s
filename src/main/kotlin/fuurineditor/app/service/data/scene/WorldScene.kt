package fuurineditor.app.service.data.scene

import fuurineditor.app.service.data.FileId

data class WorldScene(
    override val id: FileId,
    val width: Int,
    val height: Int,
    val layer: WorldLayer
) : Scene