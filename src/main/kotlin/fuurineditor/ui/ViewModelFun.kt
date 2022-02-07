package fuurineditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.lang.reflect.Constructor

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

@Composable
inline fun <reified VM : Any> viewModel(vararg param: Any): VM {

    val springContext = LocalSpringContext.current

    return remember {

        //SpringにAssistedがないので似た感じの処理を実現させる

        //型情報を取得して
        val targetClass = VM::class.java
        //Beanなので一旦決め打ちでコンストラクタを取得
        val constructor: Constructor<*> = targetClass.constructors[0]
        //コンストラクタのパラメーターを取得
        val parameterTypes: Array<Class<*>> = constructor.parameterTypes

        val diParam = mutableListOf(*param)
        for (i in param.size until parameterTypes.size) {
            //コンテナからインスタンスを持ってきて追加
            diParam += springContext.getBean(parameterTypes[i])
        }

        springContext.getBean(VM::class.java, *diParam.toTypedArray())
    }

}