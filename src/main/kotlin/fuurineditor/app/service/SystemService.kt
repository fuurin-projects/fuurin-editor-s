package fuurineditor.service

import fuurineditor.infra.repository.SystemRepository
import fuurineditor.service.data.ProjectInfoData
import kotlinx.coroutines.flow.Flow
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

    suspend fun deleteProjectInfo(projectInfoData: ProjectInfoData) {
        systemRepository.deleteProjectInfo(projectInfoData)
    }

    fun getProjectInfoList(): Flow<List<ProjectInfoData>> {
        return systemRepository.getProjectInfoList()
    }

}