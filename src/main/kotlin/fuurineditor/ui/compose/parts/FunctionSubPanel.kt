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
import androidx.compose.material.icons.sharp.CreateNewFolder
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.NoteAdd
import androidx.compose.material.icons.sharp.People
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.material.icons.sharp.Public
import androidx.compose.material.icons.sharp.WebAsset
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
    sceneList: File?,
    tiletipList: File?,
    onAddTileTip: (rowTileTip: RowTileTip) -> Unit = {},
    addEditor: (file: File) -> Unit = {},
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

            when (functionType) {
                FunctionType.Scene -> {
                    ToolButton(imageVector = Icons.Sharp.NoteAdd, onClick = {

                    })
                    ToolButton(imageVector = Icons.Sharp.CreateNewFolder, onClick = {

                    })
                }
                FunctionType.Textures -> {
                    ToolButton(imageVector = Icons.Sharp.AddPhotoAlternate, onClick = {
                        addTiletipDialog = true
                    })
                    ToolButton(imageVector = Icons.Sharp.PersonAdd, modifier = Modifier.offset(x = (-2).dp))
                }
                else -> {
                    ToolButton(imageVector = Icons.Sharp.Build)

                    Text(text = "s  ")
                    Text(text = functionType.name)
                }
            }

        }
        Divider(color = Border, thickness = 1.dp)

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

            when (functionType) {
                //Scene
                FunctionType.Scene -> {
                    TreeNode(
                        imageVector = Icons.Sharp.Public,
                        text = "Global",
                        deep = 0,
                        idDirectory = false,
                        folding = true,
                    )

                    if (sceneList != null) {
                        TreeView(
                            root = sceneList,
                            rootIcon = Icons.Sharp.WebAsset,
                            rootName = "Scene",
                            onDoubleClick = {
                                //addEditor(it)
                            }
                        )
                    }

                }
                //テクスチャ
                FunctionType.Textures -> {

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

                    } else {

                    }
                }
                else -> {
                    Text(text = "s")
                }
            }


        }


    }

}