package fuurineditor.presen.ui.compose.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.presen.ui.theme.SelectColor

@Composable
fun SubTab(
    imageVector: ImageVector?,
    text: String,
    isSelect: Boolean = false,
    onClick: () -> Unit = {},
) {

    Row(
        modifier = Modifier.clickable { onClick() }
            .drawBehind {
                drawPath(
                    Path().apply {
                        val height = size.height
                        val width = size.width
                        val strokeWidthPx = 2.dp.toPx()
                        moveTo(0f, height - strokeWidthPx)
                        lineTo(width, height - strokeWidthPx)
                        lineTo(width, height)
                        lineTo(0f, height)
                        close()
                    },
                    color = if (isSelect) SelectColor else Color.Unspecified
                )
            }.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
    ) {
        IconText(
            imageVector = imageVector,
            text = text
        )
    }

}