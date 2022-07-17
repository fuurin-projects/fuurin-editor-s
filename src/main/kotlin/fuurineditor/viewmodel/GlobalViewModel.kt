package fuurineditor.viewmodel

import fuurineditor.app.service.ProjectService
import fuurineditor.app.service.SystemService
import fuurineditor.app.service.data.ProjectInfoData
import fuurineditor.presen.ui.data.ProjectState
import fuurineditor.viewmodel.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component

/**
 * GlobalなViewModel
 *
 * 基本的にはWindowの管理をおこなう
 */
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
open class GlobalViewModel(
    private val projectService: ProjectService,
    private val systemService: SystemService
) : ViewModel() {

    //Launcherが起動しているかどうか
    private val _openLauncher = MutableStateFlow(true)
    val openLauncher: StateFlow<Boolean> = _openLauncher

    private val _openProjectList = MutableStateFlow<List<ProjectState>>(arrayListOf<ProjectState>())
    val openProjectList: StateFlow<List<ProjectState>> = _openProjectList

    fun openProject(projectState: ProjectState) {
        val value = openProjectList.value
        _openProjectList.value = value + projectState

        viewModelScope.launch {
            systemService.addProjectInfoData(
                ProjectInfoData(
                    name = projectState.name,
                    path = projectState.path
                )
            )
        }

        _openLauncher.value = false

    }

    fun closeProject(projectState: ProjectState) {
        val value = openProjectList.value
        _openProjectList.value = value - projectState
    }

}