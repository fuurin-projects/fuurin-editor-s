package fuurineditor.viewmodel.editor

import fuurineditor.service.GlobalSceneService
import fuurineditor.service.data.ProjectPath
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.event.Event
import fuurineditor.service.data.scene.GlobalScene
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SpringViewModel
class GlobalSceneEditorViewModel(
    private val projectPath: ProjectPath,
    private val sceneFile: SceneFile,
    private val globalSceneService: GlobalSceneService
) : ViewModel() {

    private val _globalScene = MutableStateFlow<GlobalScene?>(null)
    val globalScene: StateFlow<GlobalScene?> = _globalScene

    private val _selectEvent = MutableStateFlow<Event?>(null)
    val selectEvent: StateFlow<Event?> = _selectEvent

    init {

        //ファイルを開いたときの処理
        viewModelScope.launch {

            _globalScene.value = globalSceneService.loadGlobalScene(projectPath = projectPath, sceneFile = sceneFile)


        }

    }

    fun addEvent(event: Event) {

        viewModelScope.launch {

            if (_globalScene.value != null) {
                _globalScene.value = _globalScene.value!!.copy(
                    eventList = _globalScene.value!!.eventList + event
                )
            }

            globalSceneService.saveGlobalScene(projectPath = projectPath, globalScene = _globalScene.value!!)

            onSelectEvent(event = event)

        }

    }

    fun onSelectEvent(event: Event) {
        _selectEvent.value = event
    }

}