package fuurineditor.viewmodel.core

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

/**
 * コンポーネントからDIにアクセスするためのアノテーション
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Scope(
    scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE //このInstance自体は毎回生成する
)
annotation class SpringViewModel(

    @get:AliasFor(annotation = Component::class)
    val value: String = ""

)