package fuurineditor.app.service.data.event

data class Event(
    var name: String,
    val nodeList: List<EventNode> = arrayListOf()
) {
}