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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.File
import fuurineditor.ui.compose.window.AddTileTipDialog
import fuurineditor.ui.compose.window.RowTileTip
import fuurineditor.ui.data.FunctionType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border

@Composable
fun FunctionSubPanel(
    modifier: Modifier = Modifier,
    functionType: FunctionType,
    tiletipList: File?,
    onAddTileTip: (rowTileTip: RowTileTip) -> Unit = {},
) {

    var addTiletipDialog by remember { mutableStateOf(false) }

    if (addTiletipDialog) {
        AddTileTipDialog(
            onAddTiletip = {
                onAddTileTip(it)
            },
            onCloseRequest = {
                addTiletipDialog = false
            })
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {

            if (functionType == FunctionType.Textures) {
                ToolButton(imageVector = Icons.Sharp.AddPhotoAlternate, onClick = {
                    addTiletipDialog = true
                })
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

                    TreeView(
                        root = tiletipList,
                        rootIcon = Icons.Sharp.Image,
                        rootName = "Tiletip"
                    )
                    TreeNode(
                        imageVector = Icons.Sharp.People,
                        text = "Character",
                        deep = 0,
                        idDirectory = false,
                        folding = true,
                    )

                } else {

                }

            } else {
                Text(text = "s")
            }


        }


    }

}