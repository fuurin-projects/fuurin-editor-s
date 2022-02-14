package fuurineditor.service.data

import fuurineditor.ui.data.ProjectState
import java.nio.file.Path

data class ProjectData(
    val name: String
)

/**
 * ランチャーで扱う概要的なデータ
 */
data class ProjectInfoData(
    val name: String,
    val path: Path
)

fun ProjectInfoData.toProjectState(): ProjectState {
    return ProjectState(name = name, path = path)
}
