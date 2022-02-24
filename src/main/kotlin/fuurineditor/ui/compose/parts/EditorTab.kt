package fuurineditor.ui.compose.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.Description
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.ui.data.Editor
import fuurineditor.ui.theme.IconColor

@Composable
fun EditorTab(
    editor: Editor,
    onClick: () -> Unit = {},
    onClose: () -> Unit = {}
) {

    var focus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    var text by remember { mutableStateOf(if (editor.file is CustomTreeNodeFile) editor.file.customName else editor.file.name) }
    var imageVector by remember { mutableStateOf<ImageVector?>(if (editor.file is CustomTreeNodeFile) editor.file.fakeIcon else Icons.Sharp.Description) }

    Row(
        modifier = Modifier.fillMaxHeight().clickable { onClick() }.padding(4.dp)
    ) {
        IconText(
            imageVector = imageVector,
            text = text
        )

        Box(modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp)) {
            Icon(
                Icons.Sharp.Close,
                contentDescription = "Close project.",
                modifier = Modifier.size(16.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 8.dp)
                ) {
                    onClose()
                },
                tint = IconColor
            )
        }

    }

}