package fuurineditor.presen.ui.compose.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.presen.ui.theme.IconColor

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    imageVector: ImageVector?,
    text: String,
    textColor: Color = Color.Unspecified
) {

    Row(
        modifier = modifier
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Close project.",
                modifier = Modifier.size(20.dp),
                tint = IconColor
            )
        } else {
            Spacer(modifier = Modifier.size(20.dp))
        }
        Text(
            modifier = Modifier.padding(2.dp),
            text = text,
            color = textColor
        )
    }

}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    text: String,
    textColor: Color = Color.Unspecified
) {

    Row(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.size(20.dp).padding(2.dp),
            bitmap = imageBitmap,
            contentDescription = text,
            filterQuality = FilterQuality.None,
        )
        Text(
            modifier = Modifier.padding(2.dp),
            text = text,
            color = textColor
        )
    }

}