package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.TiletipFile
import fuurineditor.service.data.toIndexKye
import fuurineditor.ui.theme.Background

@Composable
fun TiletipEditor(file: TiletipFile) {

    Column(modifier = Modifier.background(Background).fillMaxSize()) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(256.dp),
                    bitmap = file.texture,
                    contentDescription = file.name,
                    filterQuality = FilterQuality.None,
                )
            }
            Text(text = file.id.toIndexKye())
        }


    }

}