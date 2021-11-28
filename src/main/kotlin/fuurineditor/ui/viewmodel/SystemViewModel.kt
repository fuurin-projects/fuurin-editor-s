package fuurineditor.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.springframework.stereotype.Component

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
open class SystemViewModel {

    //Launcherが起動しているかどうか
    private val _openLauncher = MutableStateFlow(true)
    val openLauncher: StateFlow<Boolean> = _openLauncher

}