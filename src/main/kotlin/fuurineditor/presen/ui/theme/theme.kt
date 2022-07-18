package fuurineditor.presen.ui.theme

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorPalette = lightColors(
    primary = Color(0xFF2F8DFB),
    primaryVariant = Color(0xFF0061c7)
)

@Composable
fun FuurinEditorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    MaterialTheme(
        colors = LightColorPalette,
    ) {

        CompositionLocalProvider(
            LocalScrollbarStyle provides ScrollbarStyle(
                minimalHeight = 16.dp,
                thickness = 8.dp,
                shape = MaterialTheme.shapes.small,
                hoverDurationMillis = 300,
                unhoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                hoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.50f)
            ),
            content = content
        )

    }

}