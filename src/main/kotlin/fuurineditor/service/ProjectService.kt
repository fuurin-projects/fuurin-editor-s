package fuurineditor.service

import fuurineditor.repository.ProjectRepository
import fuurineditor.service.data.ProjectData
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
open class ProjectService(
    private val projectRepository: ProjectRepository
) {

    fun getProjectData(path: Path): Flow<ProjectData> {
        return projectRepository.getProjectData(path)
    }

}