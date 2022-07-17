package fuurineditor.app.service.data.scene

import fuurineditor.app.service.data.FileId
import fuurineditor.app.service.data.event.Event

data class GlobalScene(
    override val id: FileId,
    val eventList: List<Event> = arrayListOf()
) : Scene