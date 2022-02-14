package fuurineditor.service

import fuurineditor.repository.SystemRepository
import fuurineditor.service.data.ProjectInfoData
import org.springframework.stereotype.Service

@Service
class SystemService(
    private val systemRepository: SystemRepository
) {

    fun getVersion(): String {
        return "0.0.1"
    }

    suspend fun addProjectInfoData(projectInfoData: ProjectInfoData) {
        systemRepository.addProjectInfoData(projectInfoData)
    }

}