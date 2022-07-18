package fuurineditor.presen.ui.compose.parts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fuurineditor.app.service.data.File
import fuurineditor.presen.ui.compose.parts.functionpanel.SceneFunctionPanel
import fuurineditor.presen.ui.compose.parts.functionpanel.TexturesFunctionPanel
import fuurineditor.presen.ui.compose.window.RowScene
import fuurineditor.presen.ui.compose.window.RowTileTip
import fuurineditor.presen.ui.data.FunctionType

@Composable
fun FunctionCurrentPanel(
    modifier: Modifier = Modifier,
    functionType: FunctionType,
    sceneList: File?,
    tiletipList: File?,
    onCreateScene: (rowScene: RowScene) -> Unit = {},
    onAddTileTip: (rowTileTip: RowTileTip) -> Unit = {},
    addEditor: (file: File) -> Unit = {},
    onGlobalSceneClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
    ) {

        when (functionType) {
            FunctionType.Scene -> {
                SceneFunctionPanel(
                    sceneList = sceneList,
                    addEditor = addEditor,
                    onCreateScene = onCreateScene,
                    onGlobalSceneClick = onGlobalSceneClick
                )
            }
            FunctionType.Textures -> {
                TexturesFunctionPanel(
                    tiletipList = tiletipList,
                    addEditor = addEditor,
                    onAddTileTip = onAddTileTip
                )
            }
            else -> {
                Text(text = functionType.name)
            }
        }

    }

}