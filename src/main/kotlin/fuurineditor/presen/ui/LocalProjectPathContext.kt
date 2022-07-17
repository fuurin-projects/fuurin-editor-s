package fuurineditor.presen.ui

import androidx.compose.runtime.compositionLocalOf
import fuurineditor.app.service.data.ProjectPath

val LocalProjectPathContext = compositionLocalOf<ProjectPath> {
    error("ProjectPath not found")
}