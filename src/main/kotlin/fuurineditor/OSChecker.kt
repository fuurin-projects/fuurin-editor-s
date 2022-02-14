package fuurineditor

import org.springframework.stereotype.Component
import java.util.*

@Component
class OSChecker {

    private val WIN_32 = System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("windows")

    fun isWindows() = WIN_32

}