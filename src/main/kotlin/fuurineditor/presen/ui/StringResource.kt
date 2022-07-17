package fuurineditor.presen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import org.springframework.context.MessageSource
import java.util.*

@Composable
@ReadOnlyComposable
fun stringResource(id: String, locale: Locale = Locale.ENGLISH): String {
    val messageSource = messageSource()
    return messageSource.getMessage(id, arrayOf(), locale);
}

@Composable
@ReadOnlyComposable
fun stringResource(id: String, vararg formatArgs: Any, locale: Locale = Locale.ENGLISH): String {
    val messageSource = messageSource()
    return messageSource.getMessage(id, formatArgs, locale);
}

@Composable
@ReadOnlyComposable
private fun messageSource(): MessageSource {
    LocalSpringContext.current
    return LocalSpringContext.current.getBean(
        MessageSource::class.java
    )
}