package fuurineditor

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.compose.Launcher
import org.springframework.context.annotation.AnnotationConfigApplicationContext


object FuurinEditor {

    fun init() {

        AnnotationConfigApplicationContext(FuurinEditorSpring::class.java).use { applicationContext ->
            
            application {
                CompositionLocalProvider(LocalSpringContext provides applicationContext) {
                    var windowSize = remember { mutableListOf<Int>(1) }

                    Launcher(onCloseRequest = ::exitApplication)
                }
            }

        }


    }

}