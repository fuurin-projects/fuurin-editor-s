package fuurineditor.repository

import fuurineditor.service.data.ProjectData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
open class ProjectRepository {

    fun getProjectData(path: Path): Flow<ProjectData> {

        return flow<ProjectData> {


            val text = path.resolve("game_info.json").toFile().readText()
            print(text)
            emit(ProjectData(name = text))

        }

    }

}