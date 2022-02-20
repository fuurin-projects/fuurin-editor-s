package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddPhotoAlternate
import androidx.compose.material.icons.sharp.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border

@Composable
fun FunctionSubPanel(
    modifier: Modifier = Modifier,
    functionType: FunctionType
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {

            if (functionType == FunctionType.Textures) {
                ToolButton(imageVector = Icons.Sharp.AddPhotoAlternate)
            } else {
                ToolButton(imageVector = Icons.Sharp.Build)

                Text(text = "s  ")
                Text(text = functionType.name)
            }

        }
        Divider(color = Border, thickness = 1.dp)
        Text(text = "s")
    }

}