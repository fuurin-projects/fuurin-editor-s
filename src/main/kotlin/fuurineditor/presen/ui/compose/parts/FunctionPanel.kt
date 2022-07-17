package fuurineditor.presen.ui.compose.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.Public
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.presen.ui.data.FunctionType
import fuurineditor.presen.ui.theme.FunctionIconColor
import fuurineditor.presen.ui.theme.SelectColor

@Composable
fun FunctionPanel(
    modifier: Modifier = Modifier,
    functionType: FunctionType,
    onClickFunctionButton: (type: FunctionType) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        FunctionButton(
            imageVector = Icons.Sharp.Home,
            contentDescription = "Close project.",
            tint = FunctionIconColor,
            select = functionType == FunctionType.General,
            onClick = {
                onClickFunctionButton(FunctionType.General)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Public,
            contentDescription = "Close project.",
            tint = FunctionIconColor,
            select = functionType == FunctionType.Scene,
            onClick = {
                onClickFunctionButton(FunctionType.Scene)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Image,
            contentDescription = "Close project.",
            tint = FunctionIconColor,
            select = functionType == FunctionType.Textures,
            onClick = {
                onClickFunctionButton(FunctionType.Textures)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Settings,
            contentDescription = "Close project.",
            tint = FunctionIconColor,
            select = functionType == FunctionType.Settings,
            onClick = {
                onClickFunctionButton(FunctionType.Settings)
            }
        )

    }
}

@Composable
fun FunctionButton(
    imageVector: ImageVector,
    contentDescription: String?,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    select: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.drawBehind {
            drawPath(
                Path().apply {
                    val strokeWidthPx = 8.dp.toPx()
                    moveTo(0f, 0f)
                    lineTo(strokeWidthPx, strokeWidthPx)
                    val height = size.height
                    lineTo(strokeWidthPx, height - strokeWidthPx)
                    lineTo(0f, height)
                    close()
                },
                color = if (select) SelectColor else Color.Unspecified
            )
        }.padding(4.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.padding(4.dp).size(64.dp).clickable {
                onClick()
            }
        )
    }
}