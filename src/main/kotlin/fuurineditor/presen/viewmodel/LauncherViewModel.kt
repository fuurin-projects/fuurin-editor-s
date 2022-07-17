package fuurineditor.presen.viewmodel

import fuurineditor.app.service.SystemService
import fuurineditor.app.service.data.ProjectInfoData
import fuurineditor.presen.viewmodel.core.SpringViewModel
import fuurineditor.presen.viewmodel.core.ViewModel
import kotlinx.coroutines.launch

@SpringViewModel
class LauncherViewModel(
    private val systemService: SystemService
) : ViewModel() {

    private val _projectInfoList = systemService.getProjectInfoList()
    val projectInfoList = _projectInfoList

    fun getVersion(): String {
        return systemService.getVersion()
    }

    fun deleteProjectInfo(projectInfoData: ProjectInfoData) {

        viewModelScope.launch {
            systemService.deleteProjectInfo(projectInfoData)
        }

    }

}