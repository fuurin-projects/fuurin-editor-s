package fuurineditor.ui.viewmodel

import fuurineditor.ui.data.ProjectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.springframework.stereotype.Component

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
open class SystemViewModel {

    //Launcherが起動しているかどうか
    private val _openLauncher = MutableStateFlow(true)
    val openLauncher: StateFlow<Boolean> = _openLauncher

    private val _openProjectList = MutableStateFlow<List<ProjectState>>(arrayListOf<ProjectState>())
    val openProjectList: StateFlow<List<ProjectState>> = _openProjectList

    fun openProject(projectState: ProjectState) {
        val value = openProjectList.value
        _openProjectList.value = value + projectState
    }

    fun closeProject(projectState: ProjectState) {
        val value = openProjectList.value
        _openProjectList.value = value - projectState
    }

}