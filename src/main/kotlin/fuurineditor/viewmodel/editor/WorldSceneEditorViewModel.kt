package fuurineditor.viewmodel.editor

import fuurineditor.service.TiletipService
import fuurineditor.service.data.SceneFile
import fuurineditor.service.data.TiletipFile
import fuurineditor.ui.compose.parts.editor.Slot
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.nio.file.Path

@SpringViewModel
class WorldSceneEditorViewModel(
    private val projectPath: Path,
    private val sceneFile: SceneFile,
    private val tiletipService: TiletipService
) : ViewModel() {

    private val _nowTip = MutableStateFlow<TiletipFile?>(null)
    val nowTip: StateFlow<TiletipFile?> = _nowTip

    private val _selectTip = MutableStateFlow<TiletipFile?>(null)
    val selectTip: StateFlow<TiletipFile?> = _selectTip

    //26.5 * 15
    private val _layer = MutableStateFlow<Array<Array<TiletipFile?>>>(Array(27) { arrayOfNulls(15) })
    val layer: StateFlow<Array<Array<TiletipFile?>>> = _layer

    init {

        viewModelScope.launch {

            _nowTip.value = tiletipService.getTiletip(projectPath).map { file -> file as TiletipFile }.last()

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
            _layer.value[slot.x][slot.y] = selectTip.value
            _layer.value = _layer.value.clone()
        }

    }


}