package fuurineditor.viewmodel

import fuurineditor.service.SystemService

@ViewModelBean
class LauncherViewModel(private val systemService: SystemService) : ViewModel() {

    private val _projectInfoList = systemService.getProjectInfoList()
    val projectInfoList = _projectInfoList
    
    fun getVersion(): String {
        return systemService.getVersion()
    }

}