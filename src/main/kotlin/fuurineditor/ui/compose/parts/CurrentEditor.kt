package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fuurineditor.ui.data.Editor
import fuurineditor.ui.data.EmptyEditor
import fuurineditor.ui.theme.Background

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

            Column(modifier = Modifier.background(Background).fillMaxSize()) {

                Text(text = editor.file.name)


            }

        }

    }


}