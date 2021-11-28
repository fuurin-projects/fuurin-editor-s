package fuurineditor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
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


                    if (openLauncher) {
                        Launcher(onCloseRequest = ::exitApplication)
                    }

                }

            }


        }

    }
}

