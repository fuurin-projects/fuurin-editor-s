package fuurineditor.infra.repository.data

import fuurineditor.service.data.ProjectInfoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SystemPreferenceJson(

    @SerialName("project_data_list")
    val projectDataList: List<ProjectData>

) {

    @Serializable
    data class ProjectData(
        val name: String,
        val path: String
    )

    companion object {
        val EmptySystemPreferenceJson: SystemPreferenceJson = SystemPreferenceJson(
            projectDataList = arrayListOf()
        )
    }

}

fun ProjectInfoData.toProjectData(): SystemPreferenceJson.ProjectData {
    return SystemPreferenceJson.ProjectData(
        name = name,
        path = path.toString()
    )
}



