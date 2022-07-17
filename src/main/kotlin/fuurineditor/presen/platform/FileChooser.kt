package fuurineditor.platform

import androidx.compose.ui.awt.ComposeDialog
import com.sun.glass.ui.Application
import com.sun.glass.ui.CommonDialogs
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.stage.FileChooser
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun fileChooser(
    owner: ComposeDialog,
    title: String,
    extensionFilters: List<FileChooser.ExtensionFilter>?,
) = suspendCoroutine<File?> { continuation ->

    JFXPanel()
    Platform.runLater {

        val fileChooser = FileChooser()
        fileChooser.title = title
        if (extensionFilters != null) {
            fileChooser.extensionFilters.addAll(
                *extensionFilters.toTypedArray()
            )
        }

        try {

            val nativeWindow = Application.GetApplication().createWindow(owner.windowHandle)

            val file: List<File> = CommonDialogs.showFileChooser(
                nativeWindow,
                fileChooser.initialDirectory,
                fileChooser.initialFileName,
                fileChooser.title,
                CommonDialogs.Type.OPEN,
                false,
                fileChooser.extensionFilters.map {
                    CommonDialogs.ExtensionFilter(
                        it.description,
                        it.extensions
                    )
                },
                fileChooser.extensionFilters.indexOf(fileChooser.selectedExtensionFilter)
            ).files

            if (file.isEmpty().not()) {
                //openProject(ProjectState(name = "test", path = file.toPath()))
                continuation.resume(file.get(0))
            } else {
                continuation.resume(null)
            }

        } finally {

        }

    }

}