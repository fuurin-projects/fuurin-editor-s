package fuurineditor.viewmodel

import fuurineditor.service.SystemService
import fuurineditor.service.data.ProjectInfoData
import fuurineditor.viewmodel.core.SpringViewModel
import fuurineditor.viewmodel.core.ViewModel
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