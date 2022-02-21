package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddPhotoAlternate
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.People
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.File
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border

@Composable
fun FunctionSubPanel(
    modifier: Modifier = Modifier,
    functionType: FunctionType,
    tiletipList: File?,
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {

            if (functionType == FunctionType.Textures) {
                ToolButton(imageVector = Icons.Sharp.AddPhotoAlternate)
                ToolButton(imageVector = Icons.Sharp.PersonAdd, modifier = Modifier.offset(x = (-2).dp))

            } else {
                ToolButton(imageVector = Icons.Sharp.Build)

                Text(text = "s  ")
                Text(text = functionType.name)
            }

        }
        Divider(color = Border, thickness = 1.dp)

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

            if (functionType == FunctionType.Textures) {

                if (tiletipList != null) {

                } else {
                    TreeNode(
                        imageVector = Icons.Sharp.Image,
                        text = "Tiletip"
                    )
                    TreeNode(
                        imageVector = Icons.Sharp.People,
                        text = "Character"
                    )
                }

            } else {
                Text(text = "s")
            }


        }


    }

}