package fuurineditor.service.data

import java.nio.file.Path

/**
 * Projectのパスを管理しているクラス
 */
data class ProjectPath(
    /**
     * ProjectのRootパス
     */
    val path: Path,
)