package fuurineditor.infra.repository

import fuurineditor.app.property.IProjectProperty
import fuurineditor.app.service.data.ProjectData
import fuurineditor.app.service.data.ProjectPath
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
open class ProjectRepository(
    private val projectProperty: IProjectProperty
) {

    fun getProjectData(): Flow<ProjectData> {

        return flow<ProjectData> {

            val text = projectProperty.projectPath.resolve("game_info.json").toFile().readText()
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