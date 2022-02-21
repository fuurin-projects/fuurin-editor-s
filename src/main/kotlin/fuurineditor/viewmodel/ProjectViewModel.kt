package fuurineditor.viewmodel

import fuurineditor.service.ProjectService
import fuurineditor.service.TiletipService
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectData
import fuurineditor.ui.compose.screen.ProjectScreenUIState
import fuurineditor.ui.data.FunctionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.nio.file.Path

@ViewModelBean
open class ProjectViewModel(
    private val projectPath: Path,
    private val projectService: ProjectService,
    private val tiletipService: TiletipService
) : ViewModel() {

    private val _count = MutableStateFlow<Int>(0)
    val count: StateFlow<Int> = _count

    private val _uiState = MutableStateFlow(ProjectScreenUIState())
    val uiState: StateFlow<ProjectScreenUIState> = _uiState

    private val _projectData = projectService.getProjectData(projectPath);
    val projectData: Flow<ProjectData> = _projectData

    private val _tiletipList = tiletipService.getTiletip(projectPath);
    val tiletipList: Flow<File> = _tiletipList

    fun increment() {
        _count.value = count.value + 1
    }

    fun changeFunctionType(type: FunctionType) {
        _uiState.value = _uiState.value.copy(
            functionType = type
        )
    }

}