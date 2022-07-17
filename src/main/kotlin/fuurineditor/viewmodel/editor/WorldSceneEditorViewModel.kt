package fuurineditor.viewmodel.editor

import fuurineditor.debounce
import fuurineditor.service.TiletipService
import fuurineditor.service.WorldSceneService
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.TiletipFile
import fuurineditor.service.data.scene.WorldLayer
import fuurineditor.service.data.scene.WorldScene
import fuurineditor.ui.compose.parts.editor.Slot
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SpringViewModel
class WorldSceneEditorViewModel(
    private val sceneFile: SceneFile,
    private val tiletipService: TiletipService,
    private val worldSceneService: WorldSceneService
) : ViewModel() {

    /**
     * 現在のパレットのルート
     */
    private val _nowTip = MutableStateFlow<TiletipFile?>(null)
    val nowTip: StateFlow<TiletipFile?> = _nowTip

    private val _selectTip = MutableStateFlow<TiletipFile?>(null)
    val selectTip: StateFlow<TiletipFile?> = _selectTip

    private val _worldScene = MutableStateFlow<WorldScene?>(null)
    val worldScene: StateFlow<WorldScene?> = _worldScene

    //26.5 * 15
    private val _layer = MutableStateFlow<WorldLayer>(WorldLayer(27, 15))
    val layer: StateFlow<WorldLayer> = _layer

    val setLayer = debounce<WorldLayer>(delayMillis = 3000L, coroutineScope = viewModelScope) {

        println(it)

        worldSceneService.saveWorldScene(worldScene = _worldScene.value!!)

    }

    init {

        //ファイルを開いたときの処理
        viewModelScope.launch {

            _nowTip.value = tiletipService.getTiletip().map { file -> file as TiletipFile }.last()

            _worldScene.value = worldSceneService.loadWorldScene(sceneFile = sceneFile)

            _layer.value.rowData = _worldScene.value!!.layer.rowData

        }

    }

    fun selectTiletip(file: TiletipFile) {

        if (file.isDirectory) {
            return
        }

        _selectTip.value = file

    }

    fun clickWorld(slot: Slot) {

        if (_selectTip.value != null) {
            _layer.value.rowData[slot.x][slot.y] = selectTip.value
            _layer.value = _layer.value.copy()
        }

        //更新したレイヤーを保存
        setLayer(layer.value)

    }


}