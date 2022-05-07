package fuurineditor.service.data.scene

import fuurineditor.service.data.FileId
import fuurineditor.service.data.event.Event

data class GlobalScene(
    override val id: FileId,
    val eventList: List<Event> = arrayListOf(
        Event(name = "test"), Event(name = "test2")
    )
) : Scene