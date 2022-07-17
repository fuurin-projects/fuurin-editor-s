package fuurineditor.ui.compose.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Description
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import fuurineditor.app.service.data.File
import fuurineditor.ui.theme.IconColor
import fuurineditor.ui.theme.SelectColor
import kotlinx.coroutines.CoroutineScope

@Composable
fun TreeView(
    root: File,
    customDisplay: suspend CoroutineScope.(original: File, setCustomTreeNode: (data: CustomTreeNode) -> Unit) -> Unit = { file, setCustomTreeNode ->
        setCustomTreeNode(CustomTreeNode(name = file.name, Icons.Sharp.Description))
    },
    rootName: String?,
    rootIcon: ImageVector?,
    onDoubleClick: (file: File) -> Unit = {}
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
            },
            onDoubleClick = {
                onDoubleClick(root)
            }
        )

        if (folding.not()) {
            if (root.hasChildren) {
                for (child in root.children) {

                    key(child.name) {
                        TreeDirectoryView(child, 1, customDisplay, onDoubleClick)
                    }
                }
            }
        }


    } else {
        key(root.name) {
            TreeDirectoryView(root, 0, customDisplay, onDoubleClick)
        }
    }

}

@Composable
fun TreeDirectoryView(
    root: File,
    deep: Int,
    customDisplay: suspend CoroutineScope.(original: File, setCustomTreeNode: (data: CustomTreeNode) -> Unit) -> Unit = { file, setCustomTreeNode ->
        setCustomTreeNode(CustomTreeNode(name = file.name, Icons.Sharp.Description))
    },
    onDoubleClick: (file: File) -> Unit = {}
) {

    var folding by remember { mutableStateOf(true) }

    var text by remember { mutableStateOf(if (root is CustomTreeNodeFile) root.customName else root.name) }
    var imageVector by remember { mutableStateOf<ImageVector?>(if (root is CustomTreeNodeFile) root.fakeIcon else Icons.Sharp.Description) }

    TreeNode(
        imageVector = imageVector,
        text = text,
        deep = deep,
        folding = folding,
        idDirectory = root.isDirectory,
        onClockArrow = {
            folding = folding.not()
        },
        onDoubleClick = {
            onDoubleClick(root)
        }
    )

    if (folding.not()) {
        if (root.hasChildren) {
            for (child in root.children) {
                key(child.name) {
                    TreeDirectoryView(
                        root = child,
                        deep = deep + 1,
                        onDoubleClick = onDoubleClick
                    )
                }
            }
        }
    }

    LaunchedEffect(customDisplay) {

        customDisplay(root) { customTreeNode ->

//            if (customTreeNode.name != null) {
//                text = customTreeNode.name
//            } else {
//                text = root.name
//            }
//            if (customTreeNode.icon != null) {
//                imageVector = customTreeNode.icon
//            } else {
//                imageVector = Icons.Sharp.Description
//            }
        }

    }


}

@Composable
fun TreeNode(
    imageVector: ImageVector?,
    text: String,
    deep: Int,
    folding: Boolean,
    idDirectory: Boolean,
    onClockArrow: () -> Unit = {},
    onDoubleClick: () -> Unit = {}
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
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    onDoubleClick()
                },
            )
        }
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

data class CustomTreeNode(
    val name: String?,
    val icon: ImageVector?
)

interface CustomTreeNodeFile {
    val customName: String
    val fakeIcon: ImageVector
}

interface CustomFileIcon {
    val fileIcon: ImageBitmap
}
