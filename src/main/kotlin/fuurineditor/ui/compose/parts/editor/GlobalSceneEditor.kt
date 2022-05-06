package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bolt
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.SceneFile
import fuurineditor.ui.LocalProjectPathContext
import fuurineditor.ui.compose.parts.SubTab
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.editor.GlobalSceneEditorViewModel

@Composable
fun GlobalSceneEditor(sceneFile: SceneFile) {

    val viewModel: GlobalSceneEditorViewModel = viewModel(LocalProjectPathContext.current, sceneFile)

    Column {

        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {

            SubTab(
                imageVector = Icons.Sharp.Bolt,
                text = "Event",
                isSelect = true
            )

            SubTab(
                imageVector = Icons.Sharp.Settings,
                text = "Setting"
            )


        }

        Divider(color = Border, thickness = 1.dp)

        Row {
            Column(modifier = Modifier.width(140.dp)) {

            }

            VerticalDivider(color = Border, thickness = 1.dp)
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f).background(Background)) {

                Row(
                    modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
                ) {

                    SubTab(
                        imageVector = Icons.Sharp.Bolt,
                        text = "Event1",
                        isSelect = true
                    )

                    SubTab(
                        imageVector = Icons.Sharp.Image,
                        text = "Event2"
                    )

                }

                Divider(color = Border, thickness = 1.dp)


            }

            VerticalDivider(color = Border, thickness = 1.dp)
            Column(modifier = Modifier.width(100.dp)) {

            }

        }

    }


}