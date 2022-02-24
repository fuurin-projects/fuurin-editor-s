package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import fuurineditor.service.data.File
import fuurineditor.ui.theme.IconColor
import fuurineditor.ui.theme.SelectColor

@Composable
fun TreeView(
    root: File,
    rootName: String?,
    rootIcon: ImageVector?,
) {

    var folding by remember { mutableStateOf(true) }

    if (rootName != null && rootIcon != null) {

        TreeNode(
            imageVector = rootIcon,
            text = rootName,
            deep = 0,
            folding = folding,
            idDirectory = root.isDirectory,
            onClockArrow = {
                folding = folding.not()
            }
        )

        if (folding.not()) {
            if (root.hasChildren) {
                for (child in root.children) {
                    key(child.name) {
                        TreeDirectoryView(child, 1)
                    }
                }
            }
        }


    } else {
        TreeDirectoryView(root, 0)
    }

}

@Composable
fun TreeDirectoryView(
    root: File,
    deep: Int,
) {

    var folding by remember { mutableStateOf(true) }

    TreeNode(
        imageVector = Icons.Sharp.Image,
        text = root.name,
        deep = deep,
        folding = folding,
        idDirectory = root.isDirectory,
        onClockArrow = {
            folding = folding.not()
        }
    )

    if (folding.not()) {
        if (root.hasChildren) {
            for (child in root.children) {
                key(child.name) {
                    TreeDirectoryView(child, deep + 1)
                }
            }
        }
    }


}

@Composable
fun TreeNode(
    imageVector: ImageVector,
    text: String,
    deep: Int,
    folding: Boolean,
    idDirectory: Boolean,
    onClockArrow: () -> Unit = {},
) {

    val focusRequester = remember { FocusRequester() }

    var focus by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .focusRequester(focusRequester)
        .onFocusChanged {
            focus = it.isFocused
        }.fillMaxWidth().background(
            if (focus) SelectColor else Color.Unspecified
        ).clickable { focusRequester.requestFocus() }
        .offset(x = (deep * 20).dp)
    ) {

        Row(modifier = Modifier.padding(2.dp)) {

            if (idDirectory) {
                Icon(
                    imageVector = if (folding) Icons.Sharp.KeyboardArrowRight else Icons.Sharp.KeyboardArrowDown,
                    contentDescription = "Close project.",
                    modifier = Modifier.size(20.dp).clickable {
                        onClockArrow()
                    },
                    tint = if (focus) Color.White else IconColor
                )
            } else {
                Spacer(modifier = Modifier.size(20.dp))
            }


            IconText(
                imageVector = imageVector,
                text = text,
                textColor = if (focus) Color.White else Color.Unspecified
            )

        }
    }


}
