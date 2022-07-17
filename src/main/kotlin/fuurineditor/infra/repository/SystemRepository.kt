package fuurineditor.infra.repository

import fuurineditor.OSChecker
import fuurineditor.infra.repository.data.SystemPreferenceJson
import fuurineditor.infra.repository.data.toProjectData
import fuurineditor.app.service.data.ProjectInfoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.random.Random

@Repository
open class SystemRepository(
    private val osChecker: OSChecker
) {

    private val retryState = MutableStateFlow<String>("")

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

        retryState.value = Random.nextInt().toString()

    }

    suspend fun deleteProjectInfo(projectInfoData: ProjectInfoData) {

        val systemPreferenceJsonPath = getSystemPreferenceJsonPathAndInit()
        val systemPreferenceJson: SystemPreferenceJson =
            Json.decodeFromString(systemPreferenceJsonPath.toFile().readText())

        //データがない場合は何もしない
        if (systemPreferenceJson.projectDataList.contains(projectInfoData.toProjectData()).not()) {
            return
        }

        val systemPreferenceJsonNew = systemPreferenceJson.copy(
            projectDataList = systemPreferenceJson.projectDataList - projectInfoData.toProjectData()
        )

        systemPreferenceJsonPath.toFile().writeText(Json.encodeToString(systemPreferenceJsonNew))

        retryState.value = Random.nextInt().toString()

    }

    fun getProjectInfoList(): Flow<List<ProjectInfoData>> {

        return retryState.flatMapLatest {

            flow {

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