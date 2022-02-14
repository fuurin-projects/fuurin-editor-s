package fuurineditor.repository

import fuurineditor.OSChecker
import fuurineditor.repository.data.SystemPreferenceJson
import fuurineditor.repository.data.toProjectData
import fuurineditor.service.data.ProjectInfoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

@Repository
open class SystemRepository(
    private val osChecker: OSChecker
) {


    suspend fun addProjectInfoData(projectInfoData: ProjectInfoData) {

        val systemPreferenceJsonPath = getSystemPreferenceJsonPathAndInit()
        val systemPreferenceJson: SystemPreferenceJson =
            Json.decodeFromString(systemPreferenceJsonPath.toFile().readText())

        //同じデータを登録済みなら更新しない
        if (systemPreferenceJson.projectDataList.contains(projectInfoData.toProjectData())) {
            return
        }

        val systemPreferenceJsonNew = systemPreferenceJson.copy(
            projectDataList = systemPreferenceJson.projectDataList + projectInfoData.toProjectData()
        )

        systemPreferenceJsonPath.toFile().writeText(Json.encodeToString(systemPreferenceJsonNew))

    }

    fun getProjectInfoList(): Flow<List<ProjectInfoData>> {

        return flow {

            val systemPreferenceJson: SystemPreferenceJson =
                Json.decodeFromString(getSystemPreferenceJsonPathAndInit().toFile().readText())

            emit(systemPreferenceJson.projectDataList.map {
                ProjectInfoData(
                    name = it.name,
                    path = Path.of(it.path)
                )
            })


        }

    }

    /**
     * アプリ設定のPathを取得
     *
     * ファイルがない場合は初期値で作成する
     */
    private suspend fun getSystemPreferenceJsonPathAndInit(): Path {

        val applicationFolder = if (osChecker.isWindows()) {
            Path.of(System.getenv("APPDATA")).resolve("FuurinEditorS")
        } else {
            throw Error("現時点ではWindows以外に対応していません")
        }

        //アプリケーションフォルダがなければ作成
        if (applicationFolder.exists().not()) {
            Files.createDirectories(applicationFolder)
        }

        val preference = applicationFolder.resolve("preference.json")

        if (preference.exists().not()) {
            Files.createFile(preference);
            preference.toFile().writeText(Json.encodeToString(SystemPreferenceJson.EmptySystemPreferenceJson))
        }

        return preference

    }

}