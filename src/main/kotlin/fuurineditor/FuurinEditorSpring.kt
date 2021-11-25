package fuurineditor

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource


@Configuration
@ComponentScan
open class FuurinEditorSpring {

    @Bean
    open fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasenames("values/strings/strings")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

}