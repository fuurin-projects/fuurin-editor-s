package fuurineditor.viewmodel

import fuurineditor.service.ProjectService
import fuurineditor.service.SceneService
import fuurineditor.service.TiletipService
import fuurineditor.service.data.File
import fuurineditor.service.data.ProjectData
import fuurineditor.ui.compose.screen.ProjectScreenUIState
import fuurineditor.ui.compose.window.RowScene
import fuurineditor.ui.compose.window.RowTileTip
import fuurineditor.ui.data.Editor
import fuurineditor.ui.data.EmptyEditor
import fuurineditor.ui.data.FunctionType
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.nio.file.Path

@SpringViewModel
open class ProjectViewModel(
    private val projectPath: Path,
    private val projectService: ProjectService,
    private val sceneService: SceneService,
    private val tiletipService: TiletipService
) : ViewModel() {

    private val _count = MutableStateFlow<Int>(0)
    val count: StateFlow<Int> = _count

    private val _uiState = MutableStateFlow(ProjectScreenUIState())
    val uiState: StateFlow<ProjectScreenUIState> = _uiState

    private val _editors = MutableStateFlow<List<Editor>>(listOf())
    val editors: StateFlow<List<Editor>> = _editors

    private val _selectedEditor = MutableStateFlow<Editor>(EmptyEditor)
    val selectedEditor: StateFlow<Editor> = _selectedEditor

    private val _projectData = projectService.getProjectData(projectPath);
    val projectData: Flow<ProjectData> = _projectData

    private val _sceneList = sceneService.getScene(projectPath);
    val sceneList: Flow<File> = _sceneList

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

    //------------------------
    // Scene
    //------------------------

    fun onAddScene(rowScene: RowScene) {

        viewModelScope.launch {

            sceneService.addScene(projectPath, rowScene)

        }

    }

    //------------------------
    // TileTip
    //------------------------

    fun onAddTileTip(rowTileTip: RowTileTip) {

        viewModelScope.launch {

            tiletipService.addTiletip(projectPath, rowTileTip)
        }

    }

    //------------------------
    // Editor
    //------------------------

    fun addEditor(file: File) {

        val editor = _editors.value.find {
            it.file == file
        }
        if (editor != null) {
            _selectedEditor.value = editor
        } else {
            val newEditor = Editor(file)
            _editors.value += newEditor
            _selectedEditor.value = newEditor
        }

    }

    fun closeEditor(editor: Editor) {

        val index = _editors.value.indexOf(editor)

        if (_editors.value.size <= 1) {
            _selectedEditor.value = EmptyEditor
        } else {

            //開いているタブを消そうとしている場合
            if (_selectedEditor.value == editor) {

                if (index - 1 > 0) {
                    //自分より前にタブがある場合
                    _selectedEditor.value = _editors.value[index - 1]
                } else {
                    //自分より前にタブがない場合
                    _selectedEditor.value = _editors.value[index + 1]
                }

            }


        }

        _editors.value -= editor

    }

    fun selectEditor(file: File) {

        val editor = _editors.value.find {
            it.file == file
        }

        _selectedEditor.value = editor ?: EmptyEditor

    }

}