package fuurineditor.presen.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.TiletipFile
import fuurineditor.presen.ui.compose.parts.editor.GlobalSceneEditor
import fuurineditor.presen.ui.compose.parts.editor.TiletipEditor
import fuurineditor.presen.ui.compose.parts.editor.WorldSceneEditor
import fuurineditor.presen.ui.data.Editor
import fuurineditor.presen.ui.data.EmptyEditor
import fuurineditor.presen.ui.theme.Background

@Composable
fun CurrentEditor(
    editor: Editor
) {

    Box() {

        if (editor == EmptyEditor) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Fileが開かれていません")
            }
        } else {

            when (editor.file) {
                is TiletipFile -> {
                    TiletipEditor(file = editor.file)
                }
                is SceneFile -> {
                    if (editor.file.name == "global.json") {
                        GlobalSceneEditor(editor.file)
                    } else {
                        WorldSceneEditor(editor.file)
                    }
                }
                else -> {
                    Column(modifier = Modifier.background(Background).fillMaxSize()) {

                        Text(text = editor.file.name)


                    }
                }

            }

        }

    }


}