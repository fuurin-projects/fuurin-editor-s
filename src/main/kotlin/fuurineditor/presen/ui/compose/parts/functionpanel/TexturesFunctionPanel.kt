package fuurineditor.presen.ui.compose.parts.functionpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddPhotoAlternate
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
import fuurineditor.app.service.data.File
import fuurineditor.presen.ui.compose.parts.CustomTreeNode
import fuurineditor.presen.ui.compose.parts.ToolButton
import fuurineditor.presen.ui.compose.parts.TreeNode
import fuurineditor.presen.ui.compose.parts.TreeView
import fuurineditor.presen.ui.compose.window.AddCharacterTipDialog
import fuurineditor.presen.ui.compose.window.AddTileTipDialog
import fuurineditor.presen.ui.compose.window.RowTileTip
import fuurineditor.presen.ui.theme.Background
import fuurineditor.presen.ui.theme.Border

@Composable
fun TexturesFunctionPanel(
    tiletipList: File?,
    addEditor: (file: File) -> Unit = {},
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

    var addCharacterTipDialog by remember { mutableStateOf(false) }

    if (addCharacterTipDialog) {
        AddCharacterTipDialog(
            onCloseRequest = {
                addCharacterTipDialog = false
            })
    }

    Row(
        modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
    ) {
        ToolButton(imageVector = Icons.Sharp.AddPhotoAlternate, onClick = {
            addTiletipDialog = true
        })
        ToolButton(imageVector = Icons.Sharp.PersonAdd, modifier = Modifier.offset(x = (-2).dp), onClick = {
            addCharacterTipDialog = true
        })
    }

    Divider(color = Border, thickness = 1.dp)

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        if (tiletipList != null) {

            TreeView(
                root = tiletipList,
                rootIcon = Icons.Sharp.Image,
                rootName = "Tiletip",
                customDisplay = { file, setCustomTreeNode ->

                    if (file.name.endsWith(".png")) {
                        setCustomTreeNode(
                            CustomTreeNode(
                                name = file.name.replace(".png", ""),
                                icon = Icons.Sharp.Image
                            )
                        )
                    }
                },
                onDoubleClick = {
                    addEditor(it)
                }
            )
            TreeNode(
                imageVector = Icons.Sharp.People,
                text = "Character",
                deep = 0,
                idDirectory = false,
                folding = true,
            )
        }

    }
}