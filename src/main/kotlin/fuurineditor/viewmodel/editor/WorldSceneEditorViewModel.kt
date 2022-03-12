package fuurineditor.viewmodel.editor

import fuurineditor.service.data.SceneFile
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel

@SpringViewModel
class WorldSceneEditorViewModel(
    private val sceneFile: SceneFile
) : ViewModel() {


}