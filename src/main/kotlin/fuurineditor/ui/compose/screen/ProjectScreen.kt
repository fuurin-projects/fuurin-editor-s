package fuurineditor.ui.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.Public
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.theme.IconColor

@Composable
fun ProjectScreen() {

    Column {
        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {
            Text(text = "s")
        }
        Divider(color = Border, thickness = 1.dp)
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().width(80.dp).background(Background)
            ) {

                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Sharp.Home,
                        contentDescription = "Close project.",
                        tint = IconColor,
                        modifier = Modifier.padding(4.dp).size(64.dp).clickable {

                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Sharp.Public,
                        contentDescription = "Close project.",
                        tint = IconColor,
                        modifier = Modifier.padding(4.dp).size(64.dp).clickable {

                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Sharp.Image,
                        contentDescription = "Close project.",
                        tint = IconColor,
                        modifier = Modifier.padding(4.dp).size(64.dp).clickable {

                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Sharp.Settings,
                        contentDescription = "Close project.",
                        tint = IconColor,
                        modifier = Modifier.padding(4.dp).size(64.dp).clickable {

                        }
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Border)
            )
            Column(
                modifier = Modifier.fillMaxHeight().width(160.dp).background(Background)
            ) {
                Text(text = "s")
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Border)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
                ) {
                    Text(text = "s")
                }
                Divider(color = Border, thickness = 1.dp)
                Column {
                    Text(text = "s")
                }
            }
        }
        Divider(color = Border, thickness = 1.dp)
        Row(
            modifier = Modifier.height(20.dp).fillMaxWidth().background(Background)
        ) {
            Text(text = "s")
        }
    }

}