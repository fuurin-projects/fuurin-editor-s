package fuurineditor.presen.ui.theme

import androidx.compose.desktop.DesktopTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun FuurinEditorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    DesktopTheme(
        content = content
    )

}