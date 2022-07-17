package fuurineditor.app.service.data.scene

import fuurineditor.app.service.data.TiletipFile

data class WorldLayer(
    val xSize: Int, val ySize: Int
) {

    var rowData: Array<Array<TiletipFile?>> = Array(xSize) { arrayOfNulls(ySize) }

}
