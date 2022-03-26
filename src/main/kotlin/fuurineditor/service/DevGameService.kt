package fuurineditor.service

import fuurineditor.repository.WebBuildRepository
import fuurineditor.service.data.ProjectPath
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