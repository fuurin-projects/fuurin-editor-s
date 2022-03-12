package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.SceneFile
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.editor.WorldSceneEditorViewModel

//26.5 * 15

val Transparent1 = Color(0xFF666666)
val Transparent2 = Color(0xFF999999)

@Composable
fun WorldSceneEditor(sceneFile: SceneFile) {

    val viewModel: WorldSceneEditorViewModel = viewModel(sceneFile)

    Row {
        Column(modifier = Modifier.width(100.dp)) {
            Text(text = sceneFile.name)
        }
        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f).background(Background)) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Canvas(modifier = Modifier.width(32.dp * 27).height(32.dp * 15)) {

                    val slotX = size.width / 27f;
                    val slotY = size.height / 15;

                    for (x in 0 until 27) {

                        for (y in 0 until 15) {

                            drawRect(
                                topLeft = Offset(x * slotX, y * slotY),
                                color = if ((x + y) % 2 == 0) Transparent1 else Transparent2,
                                size = Size(slotX, slotY),
                            )

                        }

                    }

                }

            }

        }
        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.width(100.dp)) {

        }
    }

}