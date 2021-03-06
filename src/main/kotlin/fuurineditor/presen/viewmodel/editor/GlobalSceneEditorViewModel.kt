package fuurineditor.presen.viewmodel.editor

import androidx.compose.ui.geometry.Offset
import fuurineditor.app.service.GlobalSceneService
import fuurineditor.app.service.data.ProjectPath
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.event.Event
import fuurineditor.app.service.data.event.EventNode
import fuurineditor.app.service.data.scene.GlobalScene
import fuurineditor.presen.viewmodel.core.SpringViewModel
import fuurineditor.presen.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

            _globalScene.value = globalSceneService.loadGlobalScene(sceneFile = sceneFile)


        }

    }

    fun addEvent(event: Event) {

        viewModelScope.launch {

            if (_globalScene.value != null) {
                _globalScene.value = _globalScene.value!!.copy(
                    eventList = _globalScene.value!!.eventList + event
                )
            }

            globalSceneService.saveGlobalScene(globalScene = _globalScene.value!!)

            onSelectEvent(event = event)

        }

    }

    fun onSelectEvent(event: Event) {

        _selectEvent.value = _globalScene.value!!.eventList.find {
            it.name == event.name
        }

    }

    fun addEventNode(eventNode: EventNode) {

        if (_selectEvent.value != null) {

            viewModelScope.launch {

                val newSelectEvent = _selectEvent.value!!.copy(
                    nodeList = _selectEvent.value!!.nodeList + eventNode
                )
                _globalScene.value = _globalScene.value!!.copy(
                    //元のEventを消して新しくEventを入れる
                    eventList = (_globalScene.value!!.eventList - _selectEvent.value!!) + newSelectEvent
                )

                globalSceneService.saveGlobalScene(globalScene = _globalScene.value!!)

                onSelectEvent(event = newSelectEvent)

            }

        }

    }

    fun dragEventNode(event: Event, eventNode: EventNode, offset: Offset) {

        val nowEvent: Event = _globalScene.value!!.eventList.find { it.name == event.name }!!

        val nowEventNode: EventNode = nowEvent.nodeList.find {
            it.id == eventNode.id
        }!!

        val newEventNode = eventNode.copyWithOffset(offset)

        val newEvent: Event = event.copy(
            nodeList = ((nowEvent.nodeList - nowEventNode) + newEventNode).sortedBy { it.id }
        )

        val newGlobalScene = _globalScene.value!!.copy(
            eventList = ((_globalScene.value!!.eventList - nowEvent) + newEvent)
        )

        viewModelScope.launch {
            globalSceneService.saveGlobalScene(globalScene = newGlobalScene)

            _globalScene.value = globalSceneService.loadGlobalScene(sceneFile = sceneFile)
            onSelectEvent(newEvent)

        }

    }

    fun connectEventNode(event: Event, from: EventNode, to: EventNode) {


        viewModelScope.launch {

            globalSceneService.connectEventNode(
                sceneFile = sceneFile,
                event = event,
                from = from,
                to = to
            )

            _globalScene.update {
                globalSceneService.loadGlobalScene(sceneFile = sceneFile)
            }
            onSelectEvent(event)

        }

    }

}