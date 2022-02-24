package fuurineditor.viewmodel

import fuurineditor.service.ProjectService
import fuurineditor.service.TiletipService
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectData
import fuurineditor.ui.compose.screen.ProjectScreenUIState
import fuurineditor.ui.compose.window.RowTileTip
import fuurineditor.ui.data.Editor
import fuurineditor.ui.data.FunctionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    private val _editors = MutableStateFlow<List<Editor>>(listOf())
    val editors: StateFlow<List<Editor>> = _editors

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

    fun onAddTileTip(rowTileTip: RowTileTip) {

        viewModelScope.launch {

            tiletipService.addTiletip(projectPath, rowTileTip)
        }

    }

    fun addEditor(file: File) {
        _editors.value += Editor(file)
    }

    fun closeEditor(editor: Editor) {
        _editors.value -= editor
    }

}