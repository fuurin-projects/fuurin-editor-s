package fuurineditor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.window.application
import fuurineditor.service.data.ProjectPath
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.compose.LauncherWindow
import fuurineditor.ui.compose.window.ProjectWindow
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.GlobalViewModel
import javafx.application.Platform
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import javax.swing.UIManager


object FuurinEditor {

    fun init() {

        //Swing関係のUIをOSに合わせる
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //DI関係
        AnnotationConfigApplicationContext(FuurinEditorSpring::class.java).use { applicationContext ->

            //Compose関係
            application {
                //DIコンテナをComposeのContextに載せてどこからでも取得できるようにする
                CompositionLocalProvider(LocalSpringContext provides applicationContext) {

                    val globalViewModel: GlobalViewModel = viewModel()
                    val openLauncher by globalViewModel.openLauncher.collectAsState()

                    //---------
                    //Window自体の表示制御
                    //---------

                    //ランチャー
                    if (openLauncher) {
                        LauncherWindow(onCloseRequest = ::exitApplication, openProject = {
                            globalViewModel.openProject(it)
                        })
                    }

                    //各種プロジェクト
                    val openProjectList by globalViewModel.openProjectList.collectAsState()

                    for (projectState in openProjectList) {
                        //プロジェクトのパスをkeyにして重複防止をしている
                        key(projectState.path) {
                            ProjectWindow(
                                projectName = projectState.name,
                                projectPath = ProjectPath(projectState.path)
                            ) {
                                globalViewModel.closeProject(projectState)
                            }
                        }

                    }

                    //Windowの数が0になったらアプリを終了する
                    LaunchedEffect(openLauncher || openProjectList.isEmpty().not()) {

                        if ((openLauncher || openProjectList.isEmpty().not()).not()) {
                            exitApplication()
                        }

                    }
                }

            }

            //FXの終了処理
            Platform.exit()


        }

    }
}

