package fuurineditor.presen.viewmodel.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class ViewModel {

    val viewModelScope: CoroutineScope
        get() {
            return CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        }

}