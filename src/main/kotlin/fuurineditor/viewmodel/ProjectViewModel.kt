package fuurineditor.viewmodel

import fuurineditor.service.ProjectService
import fuurineditor.service.data.ProjectData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

@ViewModel
open class ProjectViewModel(
    private val projectPath: Path,
    private val projectService: ProjectService
) {

    private val _count = MutableStateFlow<Int>(0)
    val count: StateFlow<Int> = _count

    private val _projectData = projectService.getProjectData(projectPath);
    val projectData: Flow<ProjectData> = _projectData

    fun increment() {
        _count.value = count.value + 1
    }

}