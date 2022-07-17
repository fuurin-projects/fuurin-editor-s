package fuurineditor.app.service.data

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