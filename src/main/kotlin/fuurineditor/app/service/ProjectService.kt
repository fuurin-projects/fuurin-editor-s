package fuurineditor.app.service

import fuurineditor.infra.repository.ProjectRepository
import fuurineditor.app.service.data.ProjectData
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
open class ProjectService(
    private val projectRepository: ProjectRepository
) {

    fun getProjectData(): Flow<ProjectData> {
        return projectRepository.getProjectData()
    }

}