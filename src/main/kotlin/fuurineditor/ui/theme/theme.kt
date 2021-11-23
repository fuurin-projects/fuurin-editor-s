package fuurineditor.ui.theme

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun FuurinEditorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    DesktopMaterialTheme(
        content = content
    )

}