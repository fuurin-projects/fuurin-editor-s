package fuurineditor.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ViewModel
open class EditorViewModel {

    private val _count = MutableStateFlow<Int>(0)
    val count: StateFlow<Int> = _count

    fun increment() {
        _count.value = count.value + 1
    }

}