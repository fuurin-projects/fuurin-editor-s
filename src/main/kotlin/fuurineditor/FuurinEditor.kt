package fuurineditor

import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import fuurineditor.ui.compose.Launcher

object FuurinEditor {

    fun init() {
        application {
            var windowSize = remember { mutableListOf<Int>(1) }

            Launcher(onCloseRequest = ::exitApplication)


        }

    }

}