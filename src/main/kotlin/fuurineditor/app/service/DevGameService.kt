package fuurineditor.app.service

import fuurineditor.infra.repository.WebBuildRepository
import fuurineditor.app.service.data.ProjectPath
import org.springframework.stereotype.Service

/**
 * 開発モードのゲーム処理をするサービス
 */
@Service
class DevGameService(
    private val webBuildRepository: WebBuildRepository
) {

    suspend fun startDevGame(path: ProjectPath) {

        //ゲームをビルド
        webBuildRepository.build(path)

    }

}