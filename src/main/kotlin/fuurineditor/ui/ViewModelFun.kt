package fuurineditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * ViewModelをDIから取得する
 */
@Composable
inline fun <reified VM : Any> viewModel(): VM {

    val springContext = LocalSpringContext.current

    return remember {
        springContext.getBean(VM::class.java)
    }

}