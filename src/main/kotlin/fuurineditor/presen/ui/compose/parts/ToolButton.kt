package fuurineditor.ui.compose.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.ui.theme.IconColor

@Composable
fun ToolButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    tint: Color = IconColor,
    onClick: () -> Unit = {},
) {

    Box(
        modifier = modifier.padding(2.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Close project.",
            //24はパディングも含んだ大きさ
            modifier = Modifier.size(24.dp).clip(
                shape = RoundedCornerShape(4.dp)
            ).clickable(

            ) {
                onClick()
            }.padding(3.dp),
            tint = tint
        )

    }

}