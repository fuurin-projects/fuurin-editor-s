package fuurineditor.ui

import androidx.compose.runtime.compositionLocalOf
import fuurineditor.service.data.ProjectPath

val LocalProjectPathContext = compositionLocalOf<ProjectPath> {
    error("ProjectPath not found")
}