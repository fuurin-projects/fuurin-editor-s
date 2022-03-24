package fuurineditor.repository

import fuurineditor.service.data.ProjectData
import fuurineditor.service.data.ProjectPath
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
open class ProjectRepository(
) {

    fun getProjectData(path: ProjectPath): Flow<ProjectData> {

        return flow<ProjectData> {

            val text = path.path.resolve("game_info.json").toFile().readText()
            print(text)
            emit(ProjectData(name = text))

        }

    }


}

/**
 * Project内のリソースのパス
 */
val ProjectPath.resources: Path
    get() {
        return this@resources.path.resolve("src/main/resources/")
    }