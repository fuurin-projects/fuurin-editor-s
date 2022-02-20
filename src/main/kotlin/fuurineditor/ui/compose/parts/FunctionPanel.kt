package fuurineditor.ui.compose.parts

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.IconColor

@Composable
fun FunctionPanel(
    modifier: Modifier = Modifier,
    onClickFunctionButton: (type: FunctionType) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        FunctionButton(
            imageVector = Icons.Sharp.Home,
            contentDescription = "Close project.",
            tint = IconColor,
            onClick = {
                onClickFunctionButton(FunctionType.General)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Public,
            contentDescription = "Close project.",
            tint = IconColor,
            onClick = {
                onClickFunctionButton(FunctionType.Scene)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Image,
            contentDescription = "Close project.",
            tint = IconColor,
            onClick = {
                onClickFunctionButton(FunctionType.Textures)
            }
        )

        FunctionButton(
            imageVector = Icons.Sharp.Settings,
            contentDescription = "Close project.",
            tint = IconColor,
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
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.padding(4.dp)
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