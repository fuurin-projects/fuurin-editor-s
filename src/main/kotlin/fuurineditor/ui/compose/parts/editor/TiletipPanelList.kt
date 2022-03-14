package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.TiletipFile

@Composable
fun TiletipPanelList(
    root: TiletipFile,
    onClickTiletip: (tiletip: TiletipFile) -> Unit = {}
) {

    if (root.hasChildren) {
        for (child in root.children) {
            key(child.name) {
                Box(modifier = Modifier.size(32.dp)) {
                    Image(
                        modifier = Modifier.size(32.dp).clickable {
                            onClickTiletip(child)
                        },
                        bitmap = child.texture,
                        contentDescription = child.name,
                        filterQuality = FilterQuality.None,
                    )
                    /*
                    println(selectTip)
                    if (selectTip?.name == child.name) {
                        Box(modifier = Modifier.drawBehind {

                            println("aas")
                            val slotX = 32.dp.roundToPx()
                            val slotY = 32.dp.roundToPx()

                            drawRect(
                                topLeft = Offset((0).toFloat(), (0).toFloat()),
                                color = SelectColor,
                                size = Size(slotX.toFloat(), slotY.toFloat()),
                                style = Stroke(width = 2.dp.toPx()),
                            )
                        }) {}
                    }*/
                }

            }
        }
    }

}