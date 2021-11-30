package fuurineditor.ui.viewmodel

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Scope(
    scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE
)
annotation class ViewModel(

    @get:AliasFor(annotation = Component::class)
    val value: String = ""

)