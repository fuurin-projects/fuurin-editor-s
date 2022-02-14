package fuurineditor.viewmodel

import fuurineditor.service.ProjectService
import fuurineditor.service.SystemService
import fuurineditor.service.data.ProjectInfoData
import fuurineditor.ui.data.ProjectState
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

    }

    fun closeProject(projectState: ProjectState) {
        val value = openProjectList.value
        _openProjectList.value = value - projectState
    }

}