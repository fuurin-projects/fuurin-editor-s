package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fuurineditor.ui.theme.IconColor
import fuurineditor.ui.theme.SelectColor

@Composable
fun TreeView() {


}

@Composable
fun TreeNode(
    imageVector: ImageVector,
    text: String
) {

    val focusRequester = remember { FocusRequester() }

    var focus by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .focusRequester(focusRequester)
        .onFocusChanged {
            focus = it.isFocused
        }.fillMaxWidth().background(
            if (focus) SelectColor else Color.Unspecified
        ).clickable { focusRequester.requestFocus() }) {

        Row(modifier = Modifier.padding(2.dp)) {

            Icon(
                imageVector = Icons.Sharp.KeyboardArrowRight,
                contentDescription = "Close project.",
                modifier = Modifier.size(20.dp),
                tint = if (focus) Color.White else IconColor
            )

            IconText(
                imageVector = imageVector,
                text = text,
                textColor = if (focus) Color.White else Color.Unspecified
            )

        }
    }


}
