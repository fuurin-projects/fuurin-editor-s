package fuurineditor.ui

import androidx.compose.runtime.staticCompositionLocalOf
import org.springframework.context.support.GenericApplicationContext

val LocalSpringContext = staticCompositionLocalOf<GenericApplicationContext> {
    error("GenericApplicationContext not found")
}