package fuurineditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import fuurineditor.FuurinEditorSpring
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * SpringのIoCコンテナを取得するためのメソッド
 */
@Composable
inline fun rememberApplicationContext(
    calculation: @DisallowComposableCalls AnnotationConfigApplicationContext.() -> Unit
): AnnotationConfigApplicationContext = remember {

    val annotationConfigApplicationContext = AnnotationConfigApplicationContext()
    annotationConfigApplicationContext.register(FuurinEditorSpring::class.java)

    annotationConfigApplicationContext.calculation()
    annotationConfigApplicationContext.refresh()

    return@remember annotationConfigApplicationContext

}

