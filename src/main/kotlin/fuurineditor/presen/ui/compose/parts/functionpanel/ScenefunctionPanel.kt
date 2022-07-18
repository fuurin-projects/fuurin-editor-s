package fuurineditor.presen.ui.compose.parts.functionpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.CreateNewFolder
import androidx.compose.material.icons.sharp.NoteAdd
import androidx.compose.material.icons.sharp.Public
import androidx.compose.material.icons.sharp.WebAsset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.app.service.data.File
import fuurineditor.presen.ui.compose.parts.ToolButton
import fuurineditor.presen.ui.compose.parts.TreeNode
import fuurineditor.presen.ui.compose.parts.TreeView
import fuurineditor.presen.ui.compose.window.AddSceneDialog
import fuurineditor.presen.ui.compose.window.RowScene
import fuurineditor.presen.ui.theme.Background
import fuurineditor.presen.ui.theme.Border

@Composable
fun SceneFunctionPanel(
    sceneList: File?,
    addEditor: (file: File) -> Unit = {},
    onCreateScene: (rowScene: RowScene) -> Unit = {},
    onGlobalSceneClick: () -> Unit = {},
) {

    var addSceneDialog by remember { mutableStateOf(false) }

    if (addSceneDialog) {
        AddSceneDialog(
            onCreateScene = {
                onCreateScene(it)
                addSceneDialog = false
            },
            onCloseRequest = {
                addSceneDialog = false
            }
        )
    }

    Row(
        modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
    ) {
        ToolButton(imageVector = Icons.Sharp.NoteAdd, onClick = {
            addSceneDialog = true
        })
        ToolButton(imageVector = Icons.Sharp.CreateNewFolder, onClick = {

        })
    }

    Divider(color = Border, thickness = 1.dp)

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        TreeNode(
            imageVector = Icons.Sharp.Public,
            text = "Global",
            deep = 0,
            idDirectory = false,
            folding = true,
            onDoubleClick = {
                onGlobalSceneClick()
            }
        )

        if (sceneList != null) {
            TreeView(
                root = sceneList,
                rootIcon = Icons.Sharp.WebAsset,
                rootName = "Scene",
                onDoubleClick = {
                    addEditor(it)
                }
            )
        }
    }

}