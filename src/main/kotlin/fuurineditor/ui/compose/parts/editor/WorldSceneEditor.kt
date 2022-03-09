package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.SceneFile
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border

@Composable
fun WorldSceneEditor(sceneFile: SceneFile) {

    Row {
        Column(modifier = Modifier.width(100.dp)) {
            Text(text = sceneFile.name)
        }
        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f).background(Background)) {
            
        }
        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.width(100.dp)) {

        }
    }

}