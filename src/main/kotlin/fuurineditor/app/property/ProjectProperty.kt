package fuurineditor.property

import java.nio.file.Path

open class ProjectProperty(

    /**
     * Projectのルートディレクトリ
     */
    override val projectPath: Path
    
) : IProjectProperty

class FakeProjectProperty : IProjectProperty {

    override val projectPath: Path
        get() = TODO("フェイクな処理なので、このエラーが表示された場合は実装がおかしいです")

}