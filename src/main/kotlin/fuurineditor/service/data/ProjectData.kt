package fuurineditor.service.data

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
