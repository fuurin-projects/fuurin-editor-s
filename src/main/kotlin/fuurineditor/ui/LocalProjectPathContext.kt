package fuurineditor.ui

import androidx.compose.runtime.compositionLocalOf
import java.nio.file.Path

val LocalProjectPathContext = compositionLocalOf<Path> {
    error("ProjectPath not found")
}