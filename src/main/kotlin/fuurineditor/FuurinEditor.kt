package fuurineditor

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.compose.Launcher
import fuurineditor.ui.viewmodel.SystemViewModel
import org.springframework.context.annotation.AnnotationConfigApplicationContext


object FuurinEditor {

    fun init() {

        AnnotationConfigApplicationContext(FuurinEditorSpring::class.java).use { applicationContext ->

            application {
                CompositionLocalProvider(LocalSpringContext provides applicationContext) {

                    val systemViewModel = LocalSpringContext.current.getBean(
                        SystemViewModel::class.java
                    )
                    val openLauncher by systemViewModel.openLauncher.collectAsState()

                    var windowSize = remember { mutableStateOf<Int>(1) }

                    var createProject by remember { mutableStateOf(false) }

                    if (openLauncher) {
                        Launcher(onCloseRequest = ::exitApplication, onNewGameClick = { createProject = true })
                    }

                    if (createProject) {

                        val state: WindowState = rememberWindowState(
                            size = WindowSize(720.dp, 400.dp), position = WindowPosition(Alignment.Center)
                        )

                        Window(
                            title = "新しいゲームを開発",
                            state = state,
                            onCloseRequest = { createProject = false },
                            icon = painterResource("fuurin_icon_16.png"),
                        ) {

                            Column {
                                Text("aaa");
                            }
                        }

                    }
                }

            }


        }

    }
}

