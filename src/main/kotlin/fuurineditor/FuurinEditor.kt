package fuurineditor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.compose.LauncherWindow
import fuurineditor.ui.compose.window.ProjectWindow
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.SystemViewModel
import javafx.application.Platform
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import javax.swing.UIManager


object FuurinEditor {

    fun init() {

        //Swing関係のUIをOSに合わせる
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        AnnotationConfigApplicationContext(FuurinEditorSpring::class.java).use { applicationContext ->

            application {
                CompositionLocalProvider(LocalSpringContext provides applicationContext) {

                    val systemViewModel: SystemViewModel = viewModel()
                    val openLauncher by systemViewModel.openLauncher.collectAsState()

                    var windowSize = remember { mutableStateOf<Int>(1) }

                    //---------
                    //Window自体の表示制御
                    //---------

                    //ランチャー
                    if (openLauncher) {
                        LauncherWindow(onCloseRequest = ::exitApplication, openProject = {
                            systemViewModel.openProject(it)
                        })
                    }

                    //各種プロジェクト
                    val openProjectList by systemViewModel.openProjectList.collectAsState()

                    for (projectState in openProjectList) {
                        //プロジェクトのパスをkeyにして重複防止をしている
                        key(projectState.path) {
                            ProjectWindow(projectName = projectState.name, projectPath = projectState.path) {
                                systemViewModel.closeProject(projectState)
                            }
                        }

                    }

                }

            }

            //FXの終了処理
            Platform.exit()


        }

    }
}

